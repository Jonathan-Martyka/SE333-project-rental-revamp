package shop.main;

import junit.framework.Assert;
import junit.framework.TestCase;
import shop.command.Command;
import shop.data.Data;
import shop.data.Record;
import shop.data.Video;
import shop.data.Inventory;
import java.util.Iterator;

// TODO:
// write an integration test that tests the data classes.
// add in some videos, check out, check in, delete videos, etc.
// check that errors are reported when necessary.
// check that things are going as expected.
public class TEST1 extends TestCase {
  private Inventory _inventory = Data.newInventory();
  public TEST1(String name) {
    super(name);
  }
  private void check(Video v, int numOwned, int numOut, int numRentals) {
    Record r = _inventory.get(v);
    Assert.assertEquals(numOwned, r.numOwned());
    Assert.assertEquals(numOut, r.numOut());
    Assert.assertEquals(numRentals, r.numRentals());
  }
    
  public void test1() {
    Command clearCmd = Data.newClearCmd(_inventory);
    clearCmd.run();
    
    Video v1 = Data.newVideo("Title1", 2000, "Director1");
    Assert.assertEquals(0, _inventory.size());
    Assert.assertTrue(Data.newAddCmd(_inventory, v1, 5).run());
    Assert.assertEquals(1, _inventory.size());
    Assert.assertTrue(Data.newAddCmd(_inventory, v1, 5).run());
    Assert.assertEquals(1, _inventory.size());
    check(v1,10,0,0);
    
    // TODO  
    Video v2 = Data.newVideo("Title2", 2001, "Director2");
    assertTrue(Data.newAddCmd(_inventory, v2, 2).run());
    assertEquals(2, _inventory.size());
    try {
    	Data.newAddCmd(null, v2, 6).run();
    	fail();
    } catch (IllegalArgumentException e) {}
    assertFalse(Data.newInCmd(_inventory, v2).run());
    
    Command checkInv1 = Data.newInCmd(_inventory, v1);
    Command checkOutv1 = Data.newOutCmd(_inventory, v1);
    Command checkInv2 = Data.newInCmd(_inventory, v2);
    Command checkOutv2 = Data.newOutCmd(_inventory, v2);
    assertTrue(checkOutv1.run());
    assertTrue(checkOutv1.run());
    assertTrue(checkOutv2.run());
    assertEquals(2, _inventory.size());
    assertEquals(2, _inventory.get(v1).numOut());
    assertEquals(10, _inventory.get(v1).numOwned());
    assertEquals(1, _inventory.get(v2).numOut());
    assertEquals(2, _inventory.get(v2).numOwned());
    assertTrue(checkInv2.run());
    assertEquals(1, _inventory.get(v2).numRentals());
    assertTrue(Data.newAddCmd(_inventory, v2, -2).run());
    assertEquals(1, _inventory.size());
    assertTrue(_inventory.get(v2) == null);
    Command undo = Data.newUndoCmd(_inventory);
    Command redo = Data.newRedoCmd(_inventory);
    assertTrue(undo.run());
    assertEquals(2, _inventory.size());
    assertTrue(_inventory.get(v2) != null);
    assertTrue(redo.run());
    assertEquals(1, _inventory.size());
    assertTrue(_inventory.get(v2) == null);
  }
}
