package com.kang.codetool.service;

import com.kang.codetool.model.CodeMakerGeneratCodeVO;
import com.kang.framework.KlMybatisTypeMap;
import com.kang.framework.db.KlFieldDescription;
import com.kang.framework.KlDbTypeMap;
import com.kang.framework.KlString;

import java.time.LocalDate;

import static com.kang.codetool.common.Constants.ENTITY_PACKAGE_NAME;
import static com.kang.codetool.common.Constants.ENTITY_CLASS_NAME_SUFFIX;

/**
 *
 */
public class GenerateJavaCodeService {

    public String refEntity(CodeMakerGeneratCodeVO vo) {
        StringBuilder result = new StringBuilder();

        result.append(KlString.format("package {0};\n\n" +
                "import lombok.*;\n" +
                "import java.util.*;\n" +
                "import java.math.*;\n\n" +
                "/**\n" +
                " * " + vo.getTable() + "表实体类\n" +
                " *\n" +
                " * @author codeTool\n" +
                " * @date " + LocalDate.now().toString() + "\n" +
                " */\n" +
                "@Data\n" +
                "public class {1} {\n\n", vo.getPackagePath().replace("{0}", ENTITY_PACKAGE_NAME), KlString.toUpperFirst(KlString.replaceUnderline(vo.getClassNameResult())) + ENTITY_CLASS_NAME_SUFFIX));

        for (KlFieldDescription f : vo.getFieldDescriptions()) {
            if (!KlString.isBlank(f.getDescription())) {
                result.append("    /**\n" +
                        "    * " + f.getDescription().replace("\n", "  ") + "\n" +
                        "    */\n");
                result.append("    private " + KlDbTypeMap.map4J(f.getDbType(), true, vo.getDatabaseType()) + " " + KlString.toLowerFirst(f.getSimpleName()) + ";\n\n");
            }
        }
        result.append("\n}");

        return result.toString();
    }

    public String refCloneObject(CodeMakerGeneratCodeVO vo) {
        StringBuilder result = new StringBuilder();

        for (KlFieldDescription f : vo.getFieldDescriptions()) {
            result.append(KlString.format("model.set{0}(vo.get{0}());\n", KlString.toUpperFirst(f.getSimpleName())));
        }

        return result.toString();
    }

    /**
     * Dao通用代码
     */
    private static String daoBaseCode(CodeMakerGeneratCodeVO vo, String content) {
        StringBuilder result = new StringBuilder();

        result.append(KlString.format("package {0};\n" +
                "\n" +
                "import java.io.IOException;\n" +
                "import java.util.*;\n" +
                "import java.math.*;\n" +
                "\n" +
                "import javax.sql.rowset.CachedRowSet;\n" +
                "\n" +
                "import {2}.{1}{4};\n" +
                "import com.jd.codetool.lib.*;\n" +
                "\n" +
                "        public class {1}Mapper {\n" +
                "        {3}\n" +
                "\n" +
                "\n" +
                "    }", KlString.format(vo.getPackagePath(), "mapper"), KlString.toUpperFirst(KlString.replaceUnderline(vo.getClassNameResult())), KlString.format(vo.getPackagePath(), "po"), content, ENTITY_CLASS_NAME_SUFFIX));

        return result.toString();
    }

    public String refMybatisMapper(CodeMakerGeneratCodeVO vo) {
        KlFieldDescription field = vo.tryToGetPrimaryKey();

        StringBuilder codeStr = new StringBuilder();
        codeStr.append(KlString.format(
                "package {0};\n" +
                        "\n" +
                        "import {1}.{2};\n" +
                        "import com.wangyin.industry.fund.app.plugin.page.domain.PageBounds;\n" +
                        "import com.wangyin.industry.fund.app.plugin.page.domain.PageList;\n" +
                        "import java.util.List;\n" +
                        "\n" +
                        "/**\n" +
                        " * " + vo.getTable() + "表数据访问接口\n" +
                        " *\n" +
                        " * @author codeTool\n" +
                        " * @date " + LocalDate.now().toString() + "\n" +
                        " */\n" +
                        "public interface {8}Mapper {\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}查询\n" +
                        "     */\n" +
                        "    {7}By{3}({4} {5});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 查询所有数据\n" +
                        "     */\n" +
                        "    List<{2}> listAll();\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询分页数据\n" +
                        "     */\n" +
                        "    List<{2}> listByPage({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询单条数据\n" +
                        "     */\n" +
                        "    {2} getByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询批量数据\n" +
                        "     */\n" +
                        "    List<{2}> listByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询分页数据\n" +
                        "     */\n" +
                        "    PageList<{2}> listByWhere({2} {6}, PageBounds pageBounds);\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询数量\n" +
                        "     */\n" +
                        "    int count({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}删除数据\n" +
                        "     */\n" +
                        "    int deleteBy{3}({4} {5});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改数据\n" +
                        "     */\n" +
                        "    int updateBy{3}({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改不为空的字段\n" +
                        "     */\n" +
                        "    int updateBy{3}Selective({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据\n" +
                        "     */\n" +
                        "    int insert({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据（仅赋值不为空的字段）\n" +
                        "     */\n" +
                        "    int insertSelective({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 批量插入数据\n" +
                        "     */\n" +
                        "    void batchInsert(List<{2}> {6}List);\n" +
                        "\n" +
                        "}",
                KlString.format(vo.getPackagePath(), "mapper"),
                KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME),
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toUpperFirst(field.getSimpleName()),
                KlDbTypeMap.map4J(field.getDbType(), true, vo.getDatabaseType()),
                KlString.toLowerFirst(field.getSimpleName()),
                KlString.toLowerFirst(vo.getClassName()),
                vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey())) ? vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + " get" : ("List<" + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + "> list"),
                vo.getClassName()))
        ;

        return codeStr.toString();
    }

    public String refService(CodeMakerGeneratCodeVO vo) {
        KlFieldDescription field = vo.tryToGetPrimaryKey();

        StringBuilder codeStr = new StringBuilder();
        codeStr.append(KlString.format(
                "package {0};\n" +
                        "\n" +
                        "import {1}.{2};\n" +
                        "import java.util.List;\n" +
                        "\n" +
                        "/**\n" +
                        " * " + vo.getTable() + "表数据服务接口\n" +
                        " *\n" +
                        " * @author codeTool\n" +
                        " * @date " + LocalDate.now().toString() + "\n" +
                        " */\n" +
                        "public interface I{8}Service {\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}查询\n" +
                        "     */\n" +
                        "    {7}By{3}({4} {5});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 查询所有数据\n" +
                        "     */\n" +
                        "    List<{2}> listAll();\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询分页数据\n" +
                        "     */\n" +
                        "    List<{2}> listByPage({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询单条数据\n" +
                        "     */\n" +
                        "    {2} getByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询批量数据\n" +
                        "     */\n" +
                        "    List<{2}> listByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询数量\n" +
                        "     */\n" +
                        "    int count({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}删除数据\n" +
                        "     */\n" +
                        "    int deleteBy{3}({4} {5});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改数据\n" +
                        "     */\n" +
                        "    int updateBy{3}({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改不为空的字段\n" +
                        "     */\n" +
                        "    int updateBy{3}Selective({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据\n" +
                        "     */\n" +
                        "    int insert({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据（仅赋值不为空的字段）\n" +
                        "     */\n" +
                        "    int insertSelective({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 批量插入数据\n" +
                        "     */\n" +
                        "    void batchInsert(List<{2}> {6}List);\n" +
                        "\n" +
                        "}",
                KlString.format(vo.getPackagePath(), "service"),
                KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME),
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toUpperFirst(field.getSimpleName()),
                KlDbTypeMap.map4J(field.getDbType(), true, vo.getDatabaseType()),
                KlString.toLowerFirst(field.getSimpleName()),
                KlString.toLowerFirst(vo.getClassName()),
                vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey())) ? vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + " get" : ("List<" + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + "> list"),
                vo.getClassName()))
        ;

        return codeStr.toString();
    }

    public String refServiceImpl(CodeMakerGeneratCodeVO vo) {
        KlFieldDescription field = vo.tryToGetPrimaryKey();

        StringBuilder codeStr = new StringBuilder();
        codeStr.append(KlString.format(
                "package {0};\n" +
                        "\n" +
                        "import {8}.I{2}Service;\n" +
                        "import {8}.I{2}Mapper;\n" +
                        "import {1};\n" +
                        "import org.springframework.beans.factory.annotation.Autowired;\n" +
                        "import java.util.List;\n" +
                        "\n" +
                        "/**\n" +
                        " * " + vo.getTable() + "表数据服务\n" +
                        " *\n" +
                        " * @author codeTool\n" +
                        " * @date " + LocalDate.now().toString() + "\n" +
                        " */\n" +
                        "public class {2}ServiceImpl implements I{2}Service {\n" +
                        "    \n" +
                        "    @Autowired\n" +
                        "    private {2}Mapper {6}Mapper;\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}查询\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public {7} {9}By{3}({4} {5}) {\n" +
                        "        return {6}Mapper.{9}By{3}({5});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 查询所有数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public List<{10}> listAll() {\n" +
                        "        return {6}Mapper.listAll();\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询分页数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public List<{10}> listByPage({10} {6}) {\n" +
                        "        return {6}Mapper.listByPage({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询单条数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public {10} getByWhere({10} {6}) {\n" +
                        "        return {6}Mapper.getByWhere({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询批量数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public List<{10}> listByWhere({10} {6}) {\n" +
                        "        return {6}Mapper.listByWhere({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询数量\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public int count({10} {6}) {\n" +
                        "        return {6}Mapper.count({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}删除数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public int deleteBy{3}({4} {5}) {\n" +
                        "        return {6}Mapper.deleteBy{3}({5});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public int updateBy{3}({10} {6}) {\n" +
                        "        return {6}Mapper.updateBy{3}({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改不为空的字段\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public int updateBy{3}Selective({10} {6}) {\n" +
                        "        return {6}Mapper.updateBy{3}Selective({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public int insert({10} {6}) {\n" +
                        "        return {6}Mapper.insert({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据（仅赋值不为空的字段）\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public int insertSelective({10} {6}) {\n" +
                        "        return {6}Mapper.insertSelective({6});\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 批量插入数据\n" +
                        "     */\n" +
                        "    @Override\n" +
                        "    public void batchInsert(List<{10}> {6}List) {\n" +
                        "        {6}Mapper.batchInsert({6}List);\n" +
                        "    }\n" +
                        "\n" +
                        "}",
                KlString.format(vo.getPackagePath(), "service.impl"),
                KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName(),
                vo.getClassName(),
                KlString.toUpperFirst(field.getSimpleName()),
                KlDbTypeMap.map4J(field.getDbType(), true, vo.getDatabaseType()),
                KlString.toLowerFirst(field.getSimpleName()),
                KlString.toLowerFirst(vo.getClassName()),
                vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey())) ? vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX : ("List<" + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + ">"),
                KlString.format(vo.getPackagePath(), "service"),
                vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey())) ? "get" : "list",
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX));

        return codeStr.toString();
    }

    public String refMybatisMapperWithAnnotation(CodeMakerGeneratCodeVO vo) {
        StringBuilder fieldParams = new StringBuilder();
        StringBuilder field_SqlWhere = new StringBuilder();

        KlFieldDescription field = vo.tryToGetPrimaryKey();

        vo.getFieldDescriptions().forEach(f -> {

            appendLineBreak(fieldParams, 70, "            \"", "\" +");
            fieldParams.append(f.getName().contains("_") ? KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + " as " + f.getSimpleName() + ", " : KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");

            field_SqlWhere.append(KlString.format(
                    "                if ({1}.get{0}() != null) {\n" +
                            "                    where.append(\" and {2} = #{{3},jdbcType={4}}\");\n" +
                            "                }\n",
                    KlString.toUpperFirst(f.getSimpleName()),
                    KlString.toLowerFirst(vo.getClassName()),
                    KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()),
                    KlString.toLowerFirst(f.getSimpleName()),
                    KlMybatisTypeMap.map4Mybatis(f.getDbType(), vo.getDatabaseType()).toUpperCase()
            ));
        });
        boolean isPKIdentity = vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey()) && f.getIsIdentity());

        StringBuilder codeStr = new StringBuilder();
        codeStr.append(KlString.format(
                "package {0};\n" +
                        "\n" +
                        "import {1};\n" +
                        "import org.apache.ibatis.annotations.*;\n" +
                        "import org.apache.ibatis.jdbc.SQL;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Map;\n" +
                        "\n" +
                        "/**\n" +
                        " * {8}表数据访问接口\n" +
                        " *\n" +
                        " * @author codeTool\n" +
                        " * @date " + LocalDate.now().toString() + "\n" +
                        " */\n" +
                        "public interface {13}Mapper{\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}查询\n" +
                        "     */\n" +
                        "    @Select(\"select {11} from {8} where {9} = #{{5},jdbcType={12}}\")\n" +
                        "    {7}By{3}({4} {5});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 查询所有数据\n" +
                        "     */\n" +
                        "    @Select(\"select {11} from {8}\")\n" +
                        "    List<{2}> listAll();\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询分页数据\n" +
                        "     */\n" +
                        "    @SelectProvider(type = {2}Provider.class, method = \"listByPage\")\n" +
                        "    List<{2}> listByPage({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询单条数据\n" +
                        "     */\n" +
                        "    @SelectProvider(type = {2}Provider.class, method = \"getByWhere\")\n" +
                        "    {2} getByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询批量数据\n" +
                        "     */\n" +
                        "    @SelectProvider(type = {2}Provider.class, method = \"listByWhere\")\n" +
                        "    List<{2}> listByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件查询数量\n" +
                        "     */\n" +
                        "    @SelectProvider(type = {2}Provider.class, method = \"count\")\n" +
                        "    int count({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}删除数据\n" +
                        "     */\n" +
                        "    @Delete(\"delete from {8} where {9}=#{{5},jdbcType={12}}\")\n" +
                        "    int deleteBy{3}({4} {5});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过条件删除数据\n" +
                        "     */\n" +
                        "    @DeleteProvider(type = {2}Provider.class, method = \"deleteByWhere\")\n" +
                        "    int deleteByWhere({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改数据\n" +
                        "     */\n" +
                        "    @UpdateProvider(type = {2}Provider.class, method = \"updateBy{3}\")\n" +
                        "    int updateBy{3}({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}修改不为空的字段\n" +
                        "     */\n" +
                        "    @UpdateProvider(type = {2}Provider.class, method = \"updateBy{3}Selective\")\n" +
                        "    int updateBy{3}Selective({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据\n" +
                        "     */\n" +
                        "    @InsertProvider(type = {2}Provider.class, method = \"insert\")\n" +
                        (isPKIdentity ?
                                "    @Options(useGeneratedKeys = true, keyProperty = \"{9}\")\n" : "") +
                        "    int insert({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 插入数据（仅赋值不为空的字段）\n" +
                        "     */\n" +
                        "    @InsertProvider(type = {2}Provider.class, method = \"insertSelective\")\n" +
                        (isPKIdentity ?
                                "    @Options(useGeneratedKeys = true, keyProperty = \"{9}\")\n" : "") +
                        "    int insertSelective({2} {6});\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 批量插入数据\n" +
                        "     */\n" +
                        "    @InsertProvider(type = {2}Provider.class, method = \"batchInsert\")\n" +
                        "    void batchInsert(@Param(\"list\") List<{2}> {6}List);\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 通过{5}列表批量修改数据\n" +
                        "     */\n" +
                        "    @UpdateProvider(type = {2}Provider.class, method = \"batchUpdate\")\n" +
                        "    void batchUpdate(@Param(\"list\") List<{2}> {6}List);\n" +
                        "\n" +
                        "    class {2}Provider extends SQL {\n" +
                        "\n" +
                        "        private String getSqlWhere({2} {6}) {\n" +
                        "            StringBuilder where = new StringBuilder(\"1 = 1\");\n" +
                        "{10}\n" +
                        "            return where.toString();\n" +
                        "        }\n\n" +
                        generateSelectMethodCode(vo) +
                        generateDeleteMethodCode(vo) +
                        generateUpdateMethodCode(vo) +
                        generateInsertMethodCode(vo) +
                        "    }\n" +
                        "}",
                KlString.format(vo.getPackagePath(), "mapper"),
                KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toUpperFirst(field.getSimpleName()),
                KlDbTypeMap.map4J(field.getDbType(), true, vo.getDatabaseType()),
                KlString.toLowerFirst(field.getSimpleName()),
                KlString.toLowerFirst(vo.getClassName()),
                vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey())) ? vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + " get" : ("List<" + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX + "> list"),
                vo.getTable(),
                field.getName(),
                field_SqlWhere,
                fieldParams.substring(0, fieldParams.length() - 2),
                KlMybatisTypeMap.map4Mybatis(field.getDbType(), vo.getDatabaseType()).toUpperCase(),
                vo.getClassName()));

        return codeStr.toString();
    }

    public String generateSelectMethodCode(CodeMakerGeneratCodeVO vo) {
        StringBuilder fieldParams = new StringBuilder();
        vo.getFieldDescriptions().forEach(f -> {
            appendLineBreak(fieldParams, 100, "                    \"", "\" +");
            fieldParams.append(f.getName().contains("_") ? KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + " as " + f.getSimpleName() + ", " : KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");
        });
        return KlString.format(
                "        public String listByPage({1} {2}) {\n" +
                        "           return new SQL()\n" +
                        "                   .SELECT(\"{0}\")\n" +
                        "                   .FROM(\"{3}\")\n" +
                        "                   .WHERE(getSqlWhere({2})).toString() + \" LIMIT #{length} OFFSET #{start}\";\n" +
                        "        }\n\n" +
                        "        public String getByWhere({1} {2}) {\n" +
                        "           return new SQL()\n" +
                        "                   .SELECT(\"{0}\")\n" +
                        "                   .FROM(\"{3}\")\n" +
                        "                   .WHERE(getSqlWhere({2})).toString() + \" LIMIT 1\";\n" +
                        "        }\n\n" +
                        "        public String listByWhere({1} {2}) {\n" +
                        "           return new SQL()\n" +
                        "                   .SELECT(\"{0}\")\n" +
                        "                   .FROM(\"{3}\")\n" +
                        "                   .WHERE(getSqlWhere({2})).toString();\n" +
                        "        }\n\n" +
                        "        public String count({1} {2}) {\n" +
                        "           return new SQL()\n" +
                        "                   .SELECT(\"count(1)\")\n" +
                        "                   .FROM(\"{3}\")\n" +
                        "                   .WHERE(getSqlWhere({2})).toString();\n" +
                        "        }\n\n",
                fieldParams.substring(0, fieldParams.length() - 2),
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toLowerFirst(vo.getClassName()),
                vo.getTable());
    }

    public String generateDeleteMethodCode(CodeMakerGeneratCodeVO vo) {
        StringBuilder fieldParams = new StringBuilder();
        vo.getFieldDescriptions().forEach(f -> {
            appendLineBreak(fieldParams, 100, "                    \"", "\" +");
            fieldParams.append(f.getName().contains("_") ? KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + " as " + f.getSimpleName() + ", " : KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");
        });
        return KlString.format(
                "        public String deleteByWhere({1} {2}) {\n" +
                        "           return new SQL()\n" +
                        "                   .DELETE_FROM(\"{0}\")\n" +
                        "                   .WHERE(getSqlWhere({2})).toString();\n" +
                        "        }\n\n",
                vo.getTable(),
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toLowerFirst(vo.getClassName()));
    }

    public String generateUpdateMethodCode(CodeMakerGeneratCodeVO vo) {

        KlFieldDescription field = vo.tryToGetPrimaryKey();

        StringBuilder fieldParams = new StringBuilder();
        StringBuilder fieldUpdateSet = new StringBuilder();
        StringBuilder fieldUpdateSetSelective = new StringBuilder();
        vo.getFieldDescriptions().forEach(f -> {
            appendLineBreak(fieldParams, 100);
            fieldParams.append(KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");

            if (!f.getIsIdentity()) {
                appendLineBreak(fieldUpdateSet, 100);
                fieldUpdateSet.append("                   .SET(\"" + KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + " = #{" + f.getSimpleName() + ",jdbcType=" + KlMybatisTypeMap.map4Mybatis(f.getDbType(), vo.getDatabaseType()).toUpperCase() + "}\")\n");

                appendLineBreak(fieldUpdateSetSelective, 100);
                fieldUpdateSetSelective.append(KlString.format(
                        "            if ({1}.get{0}() != null) {\n" +
                                "                strSet.append(\"{2} = #{{3},jdbcType={4}}, \");\n" +
                                "            }\n",
                        KlString.toUpperFirst(f.getSimpleName()),
                        KlString.toLowerFirst(vo.getClassName()),
                        KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()),
                        KlString.toLowerFirst(f.getSimpleName()),
                        KlMybatisTypeMap.map4Mybatis(f.getDbType(), vo.getDatabaseType()).toUpperCase()));
            }

        });
        return KlString.format(
                "        public String updateBy{0}({1} {2}) {\n" +
                        "           return new SQL()\n" +
                        "                   .UPDATE(\"{3}\")\n" +
                        "{6}" +
                        "                   .WHERE(\"{4} = #{{5},jdbcType={7}}\").toString();\n" +
                        "        }\n\n" +
                        "        public String updateBy{0}Selective({1} {2}) {\n" +
                        "            StringBuilder strSet = new StringBuilder();\n" +
                        "{8}" +
                        "            return \"update {3} set \" + strSet.substring(0, strSet.length() - 2) + \" where {4} = #{{5},jdbcType={7}}\";\n" +
                        "        }\n\n",
                KlString.toUpperFirst(field.getSimpleName()),
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toLowerFirst(vo.getClassName()),
                vo.getTable(),
                KlMybatisTypeMap.getSafeParam(field.getName(), vo.getDatabaseType()),
                KlString.toLowerFirst(field.getSimpleName()),
                fieldUpdateSet,
                KlMybatisTypeMap.map4MybatisPostgreSql(field.getDbType()).toUpperCase(),
                fieldUpdateSetSelective);
    }

    public static String generateInsertMethodCode(CodeMakerGeneratCodeVO vo) {

        KlFieldDescription field = vo.tryToGetPrimaryKey();

        StringBuilder fieldInsertParams = new StringBuilder();
        StringBuilder fieldInsertValues = new StringBuilder();
        StringBuilder fieldInsertValuesBatch = new StringBuilder();
        StringBuilder fieldInsertSelective = new StringBuilder();
        vo.getFieldDescriptions().forEach(f -> {
            if (!f.getIsIdentity()) {
                appendLineBreak(fieldInsertParams, 100, "                       \"", "\" +");
                fieldInsertParams.append(KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");

                appendLineBreak(fieldInsertValues, 70, "                       \"", "\" +");
                fieldInsertValues.append("#{" + KlString.toLowerFirst(f.getSimpleName()) + ",jdbcType=" + KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase() + "}, ");

                appendLineBreak(fieldInsertValuesBatch, 70, "                       \"", "\" +");
                fieldInsertValuesBatch.append("#{list[{i}]." + KlString.toLowerFirst(f.getSimpleName()) + ",jdbcType=" + KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase() + "}, ");
            }

            if (!f.getIsIdentity()) {
                appendLineBreak(fieldInsertSelective, 100);
                fieldInsertSelective.append(KlString.format(
                        "            if ({1}.get{0}() != null) {\n" +
                                "                strParams.append(\"{2}, \");\n" +
                                "                strValues.append(\"#{{3},jdbcType={4}}, \");\n" +
                                "            }\n",
                        KlString.toUpperFirst(f.getSimpleName()),
                        KlString.toLowerFirst(vo.getClassName()),
                        KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()),
                        KlString.toLowerFirst(f.getSimpleName()),
                        KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase()));
            }
        });

        return KlString.format(
                "        public String insert({0} {1}) {\n" +
                        "           return new SQL()\n" +
                        "                   .INSERT_INTO(\"{5}\")\n" +
                        "                   .VALUES(\"{6}\", \n" +
                        "                   \"{7}\").toString();\n" +
                        "        }\n\n" +
                        "        public String insertSelective({0} {1}) {\n" +
                        "           StringBuilder strParams = new StringBuilder();\n" +
                        "           StringBuilder strValues = new StringBuilder();\n" +
                        "{8}" +
                        "           return new SQL()\n" +
                        "                   .INSERT_INTO(\"{5}\")\n" +
                        "                   .VALUES(strParams.substring(0, strParams.length() - 2), strValues.substring(0, strValues.length() - 2)).toString();\n" +
                        "        }\n\n" +
                        "        public String batchInsert(Map<String, Object> map) {\n" +
                        "           List<{0}> {1}List = (List) map.get(\"list\");\n" +
                        "           StringBuilder strValues = new StringBuilder();\n" +
                        "           for(int i = 0; i < {1}List.size(); i++) {\n" +
                        "               strValues.append((\"({9}), \").replaceAll(\"\\\\{i}\", i + \"\"));\n" +
                        "           }\n" +
                        "           return \"insert into {5}({6}) values \" \n" +
                        "                  + strValues.substring(0, strValues.length() - 2);\n" +
                        "        }\n\n" +
                        "        public String batchUpdate(Map<String, Object> map) {\n" +
                        "           List<{0}> {1}List = (List) map.get(\"list\");\n" +
                        "           StringBuilder strValues = new StringBuilder();\n" +
                        "           for(int i = 0; i < {1}List.size(); i++) {\n" +
                        "               strValues.append(updateByIdSelective({1}List.get(i)) + \";\");\n" +
                        "           }\n" +
                        "           return strValues.toString();\n" +
                        "        }\n\n",
                vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                KlString.toLowerFirst(vo.getClassName()),
                KlString.toUpperFirst(field.getSimpleName()),
                KlString.toLowerFirst(field.getSimpleName()),
                field.getName(),
                vo.getTable(),
                fieldInsertParams.substring(0, fieldInsertParams.length() - 2),
                fieldInsertValues.substring(0, fieldInsertValues.length() - 2),
                fieldInsertSelective,
                fieldInsertValuesBatch.substring(0, fieldInsertValuesBatch.length() - 2));
    }

    public String refMybatisMapperXml(CodeMakerGeneratCodeVO vo) {
        StringBuilder field_Basic = new StringBuilder();
        StringBuilder fieldParams = new StringBuilder();
        StringBuilder fieldWhereParams = new StringBuilder();
        StringBuilder field_SqlContent = new StringBuilder();

        StringBuilder codeStr = new StringBuilder();
        codeStr.append("<!DOCTYPE mapper PUBLIC \" -//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\"" + KlString.format(vo.getPackagePath(), "mapper") + "." + KlString.toUpperFirst(vo.getClassName()) + "Mapper\">\n");

        vo.getFieldDescriptions().forEach(f ->
        {
            field_Basic.append(KlString.format("    <{0} column=\"" + f.getName() + "\" property=\"" + KlString.toLowerFirst(f.getSimpleName()) + "\" jdbcType=\""
                            + KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase() + "\" />\n",
                    "PRI".equals(f.getColumnKey()) ? "id" : "result"));

            appendLineBreak(fieldParams, 100, "    ");
            fieldParams.append(KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");

            fieldWhereParams.append(KlString.format("\n    <if test=\"{0} != null\">\n" +
                    "        AND {1} = #{{0},jdbcType={2}}\n" +
                    "    </if>", f.getSimpleName(), KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()), KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase()));
        });

        field_SqlContent.append(GenerateCode.mybatisSelect(vo));
        field_SqlContent.append(GenerateCode.mybatisDelete(vo));
        field_SqlContent.append(GenerateCode.mybatisInsert(vo));
        field_SqlContent.append(GenerateCode.mybatisUpdate(vo));

        codeStr.append(KlString.format(
                "  <resultMap id=\"BaseResultMap\" type=\"{0}\">\n" +
                        "{1}  </resultMap>\n" +
                        "  <sql id=\"Base_Column_List\">\n" +
                        "    {2}\n" +
                        "  </sql>\n" +
                        "  <sql id=\"Where_Column_List\">{4}\n" +
                        "  </sql>\n" +
                        "{3}\n</mapper>",
                KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                field_Basic,
                fieldParams.toString().substring(0, fieldParams.length() - 2),
                field_SqlContent,
                fieldWhereParams.toString()));

        return codeStr.toString();
    }

    public static StringBuilder appendLineBreak(StringBuilder content, int maxLength) {
        return appendLineBreak(content, maxLength, "");
    }

    private static StringBuilder appendLineBreak(StringBuilder content, int maxLength, String newLineHead) {
        String[] strArr = content.toString().split("\n");
        if (strArr[strArr.length - 1].length() > maxLength) {
            return content.append("\n" + newLineHead);
        }
        return content;
    }

    private static StringBuilder appendLineBreak(StringBuilder content, int maxLength, String newLineStart, String oldLineEnd) {
        String[] strArr = content.toString().split("\n");
        if (strArr[strArr.length - 1].length() > maxLength) {
            return content.append(oldLineEnd + "\n" + newLineStart);
        }
        return content;
    }

    static class GenerateCode {


        public static String mybatisSelect(CodeMakerGeneratCodeVO vo) {

            StringBuilder result = new StringBuilder();

            if (vo.getFieldDescriptions().size() > 0) {

                KlFieldDescription field = vo.tryToGetPrimaryKey();

                result.append(KlString.format(
                        "  <select id=\"{6}By{1}\" parameterType=\"{4}\" resultMap=\"BaseResultMap\">\n" +
                                "    select\n" +
                                "    <include refid=\"Base_Column_List\" />\n" +
                                "    from {0}\n" +
                                "    where {5} = #{{2},jdbcType={3}}\n" +
                                "  </select>\n" +
                                "  <select id=\"listAll\" resultMap=\"BaseResultMap\">\n" +
                                "    select\n" +
                                "    <include refid=\"Base_Column_List\" />\n" +
                                "    from {0}\n" +
                                "  </select>\n" +
                                "  <select id=\"listByPage\" parameterType=\"{7}\" resultMap=\"BaseResultMap\">\n" +
                                "    select\n" +
                                "    <include refid=\"Base_Column_List\" />\n" +
                                "    from {0}\n" +
                                "    <trim prefix=\"where\" prefixOverrides=\"and | or\">\n" +
                                "        <include refid = \"Where_Column_List\"></include>\n" +
                                "    </trim>\n" +
                                "    LIMIT #{length} OFFSET #{start}\n" +
                                "  </select>\n" +
                                "  <select id=\"getByWhere\" parameterType=\"{7}\" resultMap=\"BaseResultMap\">\n" +
                                "    select\n" +
                                "    <include refid=\"Base_Column_List\" />\n" +
                                "    from {0}\n" +
                                "    <trim prefix=\"where\" prefixOverrides=\"and | or\">\n" +
                                "        <include refid = \"Where_Column_List\"></include>\n" +
                                "    </trim>\n" +
                                "    LIMIT 1\n" +
                                "  </select>\n" +
                                "  <select id=\"listByWhere\" parameterType=\"{7}\" resultMap=\"BaseResultMap\">\n" +
                                "    select\n" +
                                "    <include refid=\"Base_Column_List\" />\n" +
                                "    from {0}\n" +
                                "    <trim prefix=\"where\" prefixOverrides=\"and | or\">\n" +
                                "        <include refid = \"Where_Column_List\"></include>\n" +
                                "    </trim>\n" +
                                "  </select>\n" +
                                "  <select id=\"count\" parameterType=\"{7}\" resultType=\"Integer\">\n" +
                                "    select count(1)\n" +
                                "    from {0}\n" +
                                "    <trim prefix=\"where\" prefixOverrides=\"and | or\">\n" +
                                "        <include refid = \"Where_Column_List\"></include>\n" +
                                "    </trim>\n" +
                                "  </select>\n",
                        vo.getTable(),
                        KlString.toUpperFirst(field.getName()),
                        KlString.toLowerFirst(field.getSimpleName()),
                        KlMybatisTypeMap.map4MybatisPostgreSql(field.getDbType()).toUpperCase(),
                        KlDbTypeMap.map4J(field.getDbType(), true, vo.getDatabaseType()),
                        field.getName(),
                        vo.getFieldDescriptions().stream().anyMatch(f -> "PRI".equals(f.getColumnKey())) ? "get" : "list",
                        KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX));
            }
            return result.toString();
        }

        public static String mybatisUpdate(CodeMakerGeneratCodeVO vo) {

            StringBuilder result = new StringBuilder();
            StringBuilder field_UpdateParams = new StringBuilder();
            StringBuilder field_UpdateParamsSelective = new StringBuilder();

            if (vo.getFieldDescriptions().size() == 0) {
                return "";
            }
            vo.getFieldDescriptions().stream().filter(f -> f.getColumnKey() != "PRI").forEach(f ->
            {
                field_UpdateParams.append(KlString.format("{2} = #{{0},jdbcType={1}}, ", KlString.toLowerFirst(f.getSimpleName()), KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase(), KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType())));
                appendLineBreak(field_UpdateParams, 100, "    ");

                field_UpdateParamsSelective.append(KlString.format(
                        "      <if test=\"{0} != null\">\n" +
                                "        {1} = #{{0},jdbcType={2}},\n" +
                                "      </if>\n", KlString.toLowerFirst(f.getSimpleName()), KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()), KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase()));
            });

            KlFieldDescription field = vo.tryToGetPrimaryKey();

            result.append(KlString.format(
                    "  <update id=\"updateBy{7}\" parameterType=\"{4}\">\n" +
                            "    update {0}\n" +
                            "    set {6}\n" +
                            "    where {1} = #{{2},jdbcType={3}}\n" +
                            "  </update>\n" +
                            "  <update id=\"updateBy{7}Selective\" parameterType=\"{4}\">\n" +
                            "    update {0}\n" +
                            "    <set>\n" +
                            "{5}    </set>\n" +
                            "    where {1} = #{{2},jdbcType={3}}\n" +
                            "  </update>",
                    vo.getTable(),
                    field.getName(),
                    KlString.toLowerFirst(field.getSimpleName()),
                    KlMybatisTypeMap.map4MybatisPostgreSql(field.getDbType()).toUpperCase(),
                    KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX,
                    field_UpdateParamsSelective,
                    field_UpdateParams.toString().trim().substring(0, field_UpdateParams.toString().trim().length() - 1), KlString.toUpperFirst(field.getSimpleName())))
            ;
            return result.toString();
        }

        public static String mybatisInsert(CodeMakerGeneratCodeVO vo) {

            StringBuilder result = new StringBuilder();
            StringBuilder field_Params = new StringBuilder();
            StringBuilder field_InsertParams = new StringBuilder();
            StringBuilder field_InsertParams_Batch = new StringBuilder();
            StringBuilder field_InsertSelectiveParams = new StringBuilder();
            StringBuilder field_InsertSelectiveValues = new StringBuilder();

            if (vo.getFieldDescriptions().size() == 0) {
                return "";
            }
            vo.getFieldDescriptions().stream().filter(f -> !f.getIsIdentity()).forEach(f ->
            {
                appendLineBreak(field_Params, 100, "    ");
                field_Params.append(KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType()) + ", ");

                appendLineBreak(field_InsertParams, 100, "    ");
                field_InsertParams.append(KlString.format("    #{{0},jdbcType={1}},\n", KlString.toLowerFirst(f.getSimpleName()), KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase()));

                appendLineBreak(field_InsertParams_Batch, 100, "        ");
                field_InsertParams_Batch.append(KlString.format("#{item.{0},jdbcType={1}}, ", KlString.toLowerFirst(f.getSimpleName()), KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase()));

                field_InsertSelectiveParams.append(KlString.format(
                        "      <if test=\"{0} != null\">\n" +
                                "        {1},\n" +
                                "      </if>\n", KlString.toLowerFirst(f.getSimpleName()), KlMybatisTypeMap.getSafeParam(f.getName(), vo.getDatabaseType())));

                field_InsertSelectiveValues.append(KlString.format(
                        "      <if test=\"{0} != null\">\n" +
                                "        #{{0},jdbcType={1}},\n" +
                                "      </if>\n", KlString.toLowerFirst(f.getSimpleName()), KlMybatisTypeMap.map4MybatisPostgreSql(f.getDbType()).toUpperCase()));
            });

            result.append(KlString.format(
                    "  <insert id=\"insert\" parameterType=\"{3}\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" +
                            "    insert into {0}(\n" +
                            "    {1}\n" +
                            "    ) values (\n" +
                            "    {2})\n" +
                            "  </insert>\n",
                    vo.getTable(),
                    field_Params.toString().substring(0, field_Params.length() - 2),
                    field_InsertParams.toString().trim().substring(0, field_InsertParams.toString().trim().length() - 1),
                    KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX));

            result.append(KlString.format(
                    "  <insert id=\"insertSelective\" parameterType=\"{3}\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" +
                            "    insert into {0}\n" +
                            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
                            "{1}    </trim>\n" +
                            "    <trim prefix=\"values(\" suffix=\")\" suffixOverrides=\",\">\n" +
                            "{2}    </trim>\n" +
                            "  </insert>\n",
                    vo.getTable(),
                    field_InsertSelectiveParams.toString(),
                    field_InsertSelectiveValues.toString(),
                    KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX));

            result.append(KlString.format(
                    "  <insert id=\"batchInsert\" parameterType=\"java.util.List\">\n" +
                            "    insert into {0}(\n" +
                            "    {1}\n" +
                            "    ) values \n" +
                            "      <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">\n" +
                            "        ({2})\n" +
                            "      </foreach>\n" +
                            "  </insert>\n",
                    vo.getTable(),
                    field_Params.toString().substring(0, field_Params.length() - 2),
                    field_InsertParams_Batch.toString().trim().substring(0, field_InsertParams_Batch.toString().trim().length() - 1).trim(),
                    KlString.format(vo.getPackagePath(), ENTITY_PACKAGE_NAME) + "." + vo.getClassName() + ENTITY_CLASS_NAME_SUFFIX));
            return result.toString();
        }

        public static String mybatisDelete(CodeMakerGeneratCodeVO vo) {

            StringBuilder result = new StringBuilder();

            if (vo.getFieldDescriptions().size() == 0) {
                return "";
            }
            KlFieldDescription field = vo.tryToGetPrimaryKey();

            result.append(KlString.format(
                    "  <delete id=\"deleteBy{1}\" parameterType=\"{4}\">\n" +
                            "    delete from {0}\n" +
                            "    where {5} = #{{2},jdbcType={3}}\n" +
                            "  </delete>\n", vo.getTable(), KlString.toUpperFirst(field.getName()), KlString.toLowerFirst(field.getSimpleName()),
                    KlMybatisTypeMap.map4MybatisPostgreSql(field.getDbType()).toUpperCase(), KlDbTypeMap.map4J(field.getDbType(), true, vo.getDatabaseType()), field.getName()));

            return result.toString();
        }
    }
}
