package scorex.transaction

import scorex.block.Block
import scorex.block.Block.BlockId
import scorex.transaction.BlockStorage.{Direction, Forward, Reversed}

import scala.util.Try

/**
  * Abstract functional interface of state which is a result of a sequential blocks applying
  */
trait State {
  /**
    * Apply block to current state or revert it, if reversal=true.
    */
  private[transaction] def processBlock(block: Block): Try[State]

  /**
    * Determine whether a transaction was already processed or not.
    *
    * @param tx - a transaction to check
    * @return whether transaction is already included into a state or not
    */
  def included(tx: Transaction): Option[BlockId]

  def copyTo(fileNameOpt: Option[String]): State

  def isValid(txs: Seq[Transaction]): Boolean = validate(txs).size == txs.size

  def validate(txs: Seq[Transaction]): Seq[Transaction]
}
