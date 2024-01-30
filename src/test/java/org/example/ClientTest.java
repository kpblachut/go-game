package org.example;

import org.example.client.Client;

import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
public class ClientTest {

    @Test
    public void testStartClient() {
        Client mockedClient = Mockito.mock(Client.class);
        mockedClient.startClient();
        Mockito.verify(mockedClient, Mockito.times(1)).startClient();
    }
}
