package by.bsuir.cb.design.code;

import java.util.List;

public interface IScopable {

  /**
   * Returns immediate children of this node.
   *
   * @return list with immediate node's children if any, empty list otherwise.
   */
  List<IGenerative> getChildren();

  /**
   * Insert child to the given index among child nodes.
   *
   * @param index where child should be inserted.
   * @param child the IGenerative to add.
   */
  void addChild(int index, IGenerative child);

  /**
   * Appends child node.
   *
   * @param child the IGenerative to add.
   */
  void appendChild(IGenerative child);

  /**
   * Removes node's child.
   *
   * @param child the IGenerative to remove.
   */
  void removeChild(IGenerative child);

}
