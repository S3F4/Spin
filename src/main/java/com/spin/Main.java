package com.spin;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {
    private static enum RelTypes implements RelationshipType
    {
        KNOWS,
        YONELME,
        COGUL,
    }
    GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Relationship relationship;

    public void x(){
        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabase("/var/lib/neo4j/data/graph.db");
        try
        {
            Transaction tx = graphDb.beginTx();

            firstNode = graphDb.createNode();
            firstNode.setProperty( "message", "Hello, " );
            secondNode = graphDb.createNode();
            secondNode.setProperty( "message", "World!" );

            relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship.setProperty( "message", "brave Neo4j " );
            System.out.print( firstNode.getProperty( "message" ) );
            System.out.print( relationship.getProperty( "message" ) );
            System.out.print( secondNode.getProperty( "message" ) );

            tx.success();
        }catch(Exception e){
            e.printStackTrace();
        }
        registerShutdownHook( graphDb );
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.x();
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
}
