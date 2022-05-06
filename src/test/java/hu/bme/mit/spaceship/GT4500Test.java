package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTSP;
  private TorpedoStore mockTSS; 


  @BeforeEach
  public void init(){
    mockTSP = mock(TorpedoStore.class);
    mockTSS = mock(TorpedoStore.class); 
    this.ship = new GT4500(mockTSP, mockTSS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTSP.fire(1)).thenReturn(true); 
    when(mockTSS.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSP, times(1)).fire(1);
    verify(mockTSS, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTSP.fire(1)).thenReturn(true); 
    when(mockTSS.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockTSP, times(1)).fire(1);
    verify(mockTSS, times(1)).fire(1); 
  }

  @Test
  public void  fireTorpedo_All_First_Success_Only(){
    when(mockTSP.fire(1)).thenReturn(true);
    when(mockTSS.isEmpty()).thenReturn(true);  

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(true, result);
    verify(mockTSP, times(1)).fire(1);
    verify(mockTSS, times(0)).fire(1); 
  } 

  @Test
  public void  fireTorpedo_All_Second_Success_Only(){
    when(mockTSS.fire(1)).thenReturn(true);
    when(mockTSP.isEmpty()).thenReturn(true);  

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(true, result);
    verify(mockTSP, times(0)).fire(1); 
    verify(mockTSS, times(1)).fire(1);
  } 

  @Test
  public void  fireTorpedo_All_Both_Empty(){
    when(mockTSP.isEmpty()).thenReturn(true);  
    when(mockTSS.isEmpty()).thenReturn(true);  


    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);
    verify(mockTSP, times(0)).fire(1); 
    verify(mockTSS, times(0)).fire(1);
  } 


  @Test
  public void  fireTorpedo_SINGLE_Fire_First_Time_From_Secondary(){
    when(mockTSS.fire(1)).thenReturn(true);
    when(mockTSP.isEmpty()).thenReturn(true);  

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(mockTSP, times(0)).fire(1); 
    verify(mockTSS, times(1)).fire(1);
  } 

  @Test
  public void  fireTorpedo_SINGLE_Fire_Twice_Success(){
    when(mockTSS.fire(1)).thenReturn(true);
    when(mockTSP.fire(1)).thenReturn(true);

    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);


    assertEquals(true, result1 && result2);
    verify(mockTSP, times(1)).fire(1); 
    verify(mockTSS, times(1)).fire(1);
  } 

  @Test
  public void  fireTorpedo_SINGLE_Fire_Twice_Second_Store_Empty(){
    when(mockTSP.fire(1)).thenReturn(true);
    when(mockTSS.isEmpty()).thenReturn(true);

    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);


    assertEquals(true, result1 && result2);
    verify(mockTSP, times(2)).fire(1); 
    verify(mockTSS, times(0)).fire(1);
  } 

  @Test
  public void  fireTorpedo_SINGLE_Primary_Store_Fails(){
    when(mockTSP.fire(1)).thenReturn(false);
    when(mockTSS.fire(1)).thenReturn(false);


    boolean result = ship.fireTorpedo(FiringMode.SINGLE);


    assertEquals(false, result);
    verify(mockTSP, times(1)).fire(1); 
    verify(mockTSS, times(0)).fire(1);
  } 

  @Test
  public void  fireTorpedo_SINGLE_Both_Empty(){
    when(mockTSP.isEmpty()).thenReturn(true);
    when(mockTSS.isEmpty()).thenReturn(true);


    boolean result = ship.fireTorpedo(FiringMode.SINGLE);


    assertEquals(false, result);
    verify(mockTSP, times(0)).fire(1); 
    verify(mockTSS, times(0)).fire(1);
  } 

  @Test
  public void  fireTorpedo_SINGLE_Both_Empty_Second_Time(){
    when(mockTSP.isEmpty()).thenReturn(false);
    when(mockTSS.isEmpty()).thenReturn(true);
    when(mockTSS.fire(1)).thenReturn(true);

    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    when(mockTSP.isEmpty()).thenReturn(true); 
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result1);
    assertEquals(false, result2);

    verify(mockTSP, times(1)).fire(1); 
    verify(mockTSS, times(0)).fire(1);
  } 
}
