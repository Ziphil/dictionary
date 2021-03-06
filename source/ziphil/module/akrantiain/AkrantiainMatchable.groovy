package ziphil.module.akrantiain

import groovy.transform.CompileStatic
import ziphilib.transform.ConvertPrimitives


@CompileStatic @ConvertPrimitives
public interface AkrantiainMatchable {

  // ちょうど from で与えられた位置から右向きにマッチするかどうかを調べます。
  // マッチした場合はマッチした範囲の右端のインデックス (範囲にそのインデックス自体は含まない) を返します。
  // マッチしなかった場合は -1 を返します。
  public Int matchRight(AkrantiainElementGroup group, Int from, AkrantiainModule module)

  // ちょうど to で与えられた位置から左向きにマッチするかどうかを調べます。
  // マッチした場合はマッチした範囲の左端のインデックス (範囲にそのインデックス自体を含む) を返します。
  // マッチしなかった場合は -1 を返します。
  public Int matchLeft(AkrantiainElementGroup group, Int to, AkrantiainModule module)

  // モジュールに存在していない識別子を含んでいればそれを返し、そうでなければ null を返します。
  public AkrantiainToken findUnknownIdentifier(AkrantiainModule module)

  // 中身を全て展開したときに identifiers に含まれる識別子トークンが含まれていればそれを返し、そうでなければ null を返します。
  // 識別子の定義に循環参照がないかを調べるのに用いられます。
  public AkrantiainToken findCircularIdentifier(List<AkrantiainToken> identifiers, AkrantiainModule module)

  // 変換先をもつならば true を返し、そうでなければ false を返します。
  public Boolean isConcrete()

}