## 数组

### 704.二分查找

每次我在实现二分查找算法的时候，总是拿不准，Right是应该等于Mid - 1呢，还是应该等于Mid呢？同理Left是应该等于Mid + 1 呢，还是Mid呢？

还有，循环条件到底是 `while(left <= right)`呢？还是要把等号去掉呢？

这个问题是我没有明确定义查找的区间的开闭导致的。

假如我们使用闭区间的查找，也即，数组的左边界`Left`和右边界`Right`都是查找的一部分，也要检查它是否等于target，那么，当我们减半的时候，Mid所对应的位置，就要去掉它。

也即，`if (nums[middle] > target)` `right` 要赋值为 `middle - 1`，因为当前这个`nums[middle]`一定不是target，那么接下来要查找的左区间结束下标位置就是 `middle - 1`（也就是新的右边界是middle - 1，因为`nums[mid]`）

而假如我们，在这种情况下让`right = mid`，那么由于`nums[mid]`是已经查找过确定不可能是`target`的，按照我们闭区间的定义，是不应该把`nums[mid]`包含在这个闭区间的右端点的，这是违背了我们的区间定义的。

而`while`呢，也是一样的，因为当`Left == Right`的时候，数组还剩这最后一个元素处于闭区间内，理应要进入循环，检查唯一的这个元素是否等于`target`的。所以循环的条件是 `while (left <= right) `

这也就是所谓的循环不变量原则。

大家写二分法经常写乱，主要是因为**对区间的定义没有想清楚，区间的定义就是不变量**。要在二分查找的过程中，保持不变量，就是在while寻找中每一次边界的处理都要坚持根据区间的定义来操作，这就是**循环不变量**规则。

```java
public int BinarySearch(int[] nums, int target) {
    int Left_Index = 0, Right_Index = nums.length - 1;
    while (Left_Index <= Right_Index) {
        int Mid_Index = (Left_Index + Right_Index) / 2; // 防止溢出 可使用 left + ((right - left) / 2)
        if (nums[Mid_Index] > target) {
            Right_Index = Mid_Index - 1;
        }
        else if (nums[Mid_Index] < target) {
            Left_Index = Mid_Index + 1;
        }else {
            return Mid_Index;
        }
    }
    return -1;
}
```



### 27.移除元素

暴力解法就是，遍历，每找到一个`val`，就把它后面的数组整体左移一个单位，覆盖掉`val`。

这道题的暴力解法，需要注意的是，`i < sz`这里不能是i < `nums.lenght`。因为我们是移除了元素，`nums.length`并不能实时反映数组的实际size，它只是数组的容量，而不是size。因为我们通过覆盖`val`的方式移除了一个`val`。

```java
public int removeElement(int[] nums, int val) {
        int sz = nums.length;
        for (int i = 0; i < sz; i++) {
            if (nums[i] == val) {
                MoveLeft(nums, i);
                i --;
                sz --;
            }
        }
        return sz;
    }
```



双指针法：

定义快慢指针

- 快指针Fast：寻找新数组的元素 ，新数组就是不含有目标元素的数组
- 慢指针Slow：指向更新 新数组下标的位置

快指针负责从数组头遍历到数组尾，每遇见一个不是`target`的元素，就把它覆盖带到`nums[Slow]`的位置，并且`Slow++`。

结束的时候Slow和新数组的大小刚好是一致的。

用脑子想象一下这个过程就好了。

```java
public int removeElement(int[] nums, int val) {
    int  p = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != val) {
            nums[p] = nums[i];
            p ++;
        }
    }
    return p;
}
```



### 977.有序数组的平方

给你一个按 **非递减顺序** 排序的整数数组 `nums`，返回 **每个数字的平方** 组成的新数组，要求也按 **非递减顺序** 排序。

这个其实可以结合$y = x^2$的图像来理解，`nums`就是分布在x轴上的自变量。

所以我们知道，平方后的数组的最大值肯定在数组的两端，不是最左边就是最右边，不可能是中间。

所以我们可以使用双指针x1和x2，分别从数轴左边和右边出发，双向奔赴。定义一个新数组result，和A数组一样的大小，让k指向result数组终止位置，然后把`f(x1)`和`f(x2)`的大小比一下，选出更大的那个放在`Result[k]`，然后把更大的那个对应的向他行进的方向挪一个单位指向下一个， `k --`。

```java
public int[] sortedSquares(int[] nums) {
    int[] result = new int[nums.length];
    int Left = 0, Right = nums.length - 1;
    for (int i = nums.length - 1; i >= 0; i--) {
        if ( (nums[Left] * nums[Left]) > (nums[Right] * nums[Right]) ) {
            result[i] = nums[Left] * nums[Left];
            Left ++;
        }else {
            result[i] = nums[Right] * nums[Right];
            Right --;
        }
    }
    return result;
}
```

### 209.长度最小的子数组

给定一个含有 `n` 个正整数的数组和一个正整数 `target` **。**

找出该数组中满足其和 `>= target` 的长度最小的 **连续子数组** `[numsl, numsl+1, ..., numsr-1, numsr]` ，并返回其长度。如果不存在符合条件的子数组，返回 `0` 。



暴力解法会超时：18 / 21 个通过的测试用例

- 基本思路：外层循环列举子数组的长度，从0到`nums.lenght`，内层循环列举子数组的起始位置，因为是连续的子数组，所以子数组的范围是`start`到`start + Length`，求子数组和，如果大于等于`target`就直接return `Length + 1`。

```java
public int minSubArrayLen(int target, int[] nums) {
    int Length = 0;
    for (; Length < nums.length; Length++) {
        for (int start = 0; start + Length < nums.length; start ++) {
            int Sum = getSum(nums, start, start + Length);
            if (Sum >= target) {
                return Length + 1;
            }
        }
    }
    return 0;
}
private int getSum(int[] ints, int start, int end) {
    int sum = 0;
    for (int i = start; i <= end; i++) {
        sum += ints[i];
    }
    return sum;
}
```

滑动窗口：

暴力法中，两个for循环的本质可以看做是一个列举子数组起始位置，一个列举终止位置（列举长度是等效的）。

在滑动窗口中，只用一个for循环，这个循环的索引，表示 滑动窗口的终止位置。

- 窗口内是满足其和 `>= target` 的长度最小的 连续 子数组

- 窗口的起始位置如何移动：如果当前窗口的值大于s了，窗口就要向前移动了（也就是该缩小了）。在窗口的和`>=target`的情况下（要取等于，因为去掉的那个值可能是0，不改变和但是窗口大小减一），用`while`不断地缩小窗口，找到当前最小的那个窗口。

- 窗口的结束位置如何移动：窗口的结束位置就是遍历数组的指针，也就是for循环里的索引。

以`target=7`， 数组是 `[2, 3, 1, 2, 4, 3]`为例的GIF：

<img src="https://code-thinking.cdn.bcebos.com/gifs/209.%E9%95%BF%E5%BA%A6%E6%9C%80%E5%B0%8F%E7%9A%84%E5%AD%90%E6%95%B0%E7%BB%84.gif" alt="209.长度最小的子数组" style="zoom:67%;" />

```java
public int minSubArrayLen(int target, int[] nums) {
    int sum = 0, start = 0, end, result = Integer.MAX_VALUE;
    for (end = 0; end < nums.length; end ++) {
        sum += nums[end];
        while (sum >= target) {
            int Len = end - start + 1;
            result = Math.min(Len, result);
            sum -= nums[start];
            start ++;
        }
    }
    return result == Integer.MAX_VALUE ? 0 : result;
}
```

### 59.螺旋矩阵II

这道题的关键，和二分查找一样，需要明确区间的开闭。

画出这个螺旋排列的正方形矩阵很简单，无非就是：

- 填充上行从左到右
- 填充右列从上到下
- 填充下行从右到左
- 填充左列从下到上

但是这个过程边界条件非常多，在一个循环中，如此多的边界条件，如果不按照固定规则来遍历，基本上写不清代码，就跟写二分查找一样。



这里一圈下来，我们要画每四条边，这四条边怎么画，**每画一条边都要坚持一致的左闭右开，或者左开右闭的原则**，这样这一圈才能按照统一的规则画下来。就像下面那样，这里关键之处就在于，一定要明确每条边的开闭。

至于其他的规律，多画几个情况，就不难看出：

- 如果n是偶数，那么最中间不会有一个单独的1个小方框需要填（下图的5所在的位置）。n是奇数则有单独的一个小方框。
- 我们可以一圈一圈的看，一圈又分为上、右、下、左四条边。在保证左闭右开的画图原则下，不难看出：
  1. 四条边要画的长度，每一圈是一致的。**坚持每条边一致的开闭原则才保证了这一点**。
  2. 前一圈和后一圈，每条边的长度之差为2。
  3. 前一圈和后一圈，其上边的起始位置，x和y都相差1。
  4. 同一圈，前一条边和后一条边，其起始值相差等于长度。见代码里的`StartValue`是如何计算的。总之代码写的很清楚。
- 如果n是奇数，那么最后单独剩的那个格子单独处理一下就好，因为它总是矩阵最中间的那个格子，它的坐标总是【(n-1) / 2,  (n-1) / 2】，值总是n平方。

这样不难写出代码。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20220922102236.png" alt="img" style="zoom: 33%;" />

## 链表

### 203. 移除链表元素

这里本来删除头结点需要特殊处理一下，但是，通过添加一个虚拟头结点，可以统一处理的逻辑。

逻辑的重点在于，`else`里才需要调整`Prev`。不是在if里`Prev = Current`，因为Current都要被释放掉了。。

```java
        if (Current.val == val) {
            Prev.next = Current.next;
        }else {
            Prev = Prev.next;
        }
        Current = Current.next;
```

### 707. 设计链表

没什么好说的

### 707. 设计链表

属于是重量级了

递归的方法属于是重量级

时刻**注意递归的含义**。

```java
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next); //将以head.next为首的链表翻转
        head.next.next = head; //让newHead的最后一个节点(也即head.next)的next域指向head,让head成为最后一个节点
        head.next = null; //head作为最后一个节点，其的next域应为null
        return newHead;
    }
```

### 24. 两两交换链表中的节点

这道题算法上没什么难度，直接迭代的话多注意边界情况的判别就好。

还有一个递归的版本，可以研究一下

### 19.删除链表的倒数第N个节点

这道题，**使用虚拟头结点**可以简化代码。

最开始的时候快慢指针都指向虚拟头结点，然后先让快指针走`N`步，然后再让快慢指针同时往前走。**这样当快指针指向最后一个节点的时候，慢节点恰好指向倒数第N个节点的前一个节点**，慢指针的下一个节点就是要删除的节点。

最后要`return`的，是`VirtualHead.next`，**不是**传入的`head`。

### 面试题 02.07. 链表相交

经典老题了。

给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null

两个指针A和B，A沿着HeadA一直往前走到末尾，然后转向HeadB。B沿着HeadB一直往前走到末尾，然后转向HeadA，如果HeadA和HeadB相交，那么`A == B`会成立，如果不相交，那么A和B最终都为null，A == B还成立，所以最后`return A`或者B即可。

唯一需要注意的就是， `A == null`不能写成`A.next == null`，B那边同理，**否则在两个链表不相交的情况下，A和B永远不会到达null，因为A在到达最后一个节点的时候，下一步就直接指向另外一条链表的开头了，而不是在原有链表上更进一步指向`null`，会陷入死循环（A和B永远不会为`null`）。**

```java
        while (A != B) {
            if (A == null) {
                A = headB;
            }else {
                A = A.next;
            }
            if (B == null) {
                B = headA;
            }else {
                B = B.next;
            }
        }
```

### 142.环形链表II

题意： 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。

第一时间想到的思路：用一个Set，依次把遍历到的节点加进去，第一个重复出现的就是第一个入环节点。

比较慢，3ms

```java
public ListNode detectCycle(ListNode head) {
    Set<ListNode> NodeSet = new HashSet<>();
   ListNode p = head;
    while (p != null) {
        if (NodeSet.contains(p)) {
            return p;
        }else {
            NodeSet.add(p);
            p = p.next;
        }
    }
    return null;
}
```



另外一种，使用快慢指针加数学：快指针速度是慢指针的两倍

从头结点到环形入口节点 的节点数为x。 环形入口节点到 fast指针与slow指针相遇节点 节点数为y。 从相遇节点 再到环形入口节点节点数为 z。

那么相遇时： slow指针走过的节点数为: `x + y`， fast指针走过的节点数：`x + y + n (y + z)`，n为fast指针在环内走了n圈才遇到slow指针， （y+z）为 一圈内节点的个数。slow肯定不可能走一圈以上，因为那个时候fast走了两圈，二者之前肯定相遇过了。注意这里n一定是大于等于1的，因为 fast指针至少要多走一圈才能相遇slow指针。

因为fast指针是一步走两个节点，slow指针一步走一个节点， 所以 fast指针走过的节点数 = slow指针走过的节点数 * 2：

所以有：

- `(x + y) * 2 = x + y + n (y + z)`，n为圈数

也即：`x + y = n (y + z)`，也即：`x = n (y + z) - y`，也即：`x = (n - 1) (y + z) + z`

考虑n = 1的特殊情况：`x = z`，这意味着：**从头结点出发一个指针1，从相遇节点 也出发一个指针2，这两个指针每次只走一个节点， 那么当这两个指针相遇的时候就是 环形入口的节点**。n大于1的情况下，就只不过是指针2多走n-1圈，然后在入口节点相遇。



<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20220925103433.png" alt="img" style="zoom:33%;" />

所以解法大概就是：先用快慢指针，找到二者在环中相遇的节点，然后在二者相遇的时候，在相遇出和头结点处同时启动两个新的指针1和2，指针1和2速度相同，相遇处就是入环节点。



**看注释，解释了循环条件**

```java
public ListNode detectCycle(ListNode head) {
    ListNode fast = head, A = head;
    ListNode slow = head, B = null;
    /*
    * 因为循环里要用到fast.next.next，所以要求fast.next != null
    * 因为fast.next要用到fast，所以要求fast != null
    * 因为循环里要用到 slow.next，所以要求slow != null
    * 都是为了不触发空指针异常
    * */
    while (fast != null && fast.next != null && slow != null) {
        fast = fast.next.next;
        slow = slow.next;
        if (slow == fast) {
            B = slow;
            while (A !=  B) {
                A = A.next;
                B = B.next;
            }
            return A;
        }
    }
    return null;
}
```

## 哈希

### 242.有效的字母异位词

给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。

按照定义统计就行了

```java
public boolean isAnagram(String s, String t) {
    int[] frequency = new int[26];
    int[] frequency1 = new int[26];
    char[] chars = s.toCharArray();
    char[] chars1 = t.toCharArray();
    for (char aChar : chars) {
        frequency[aChar - 'a'] ++;
    }
    for (char c : chars1) {
        frequency1[c - 'a'] ++;
    }
    return Arrays.equals(frequency, frequency1);
}
```

另外一个类似的思路：

```java
public boolean isAnagram(String s, String t) {
    int[] record = new int[26];

    for (int i = 0; i < s.length(); i++) {
        record[s.charAt(i) - 'a']++;     // 并不需要记住字符a的ASCII，只要求出一个相对数值就可以了
    }

    for (int i = 0; i < t.length(); i++) {
        record[t.charAt(i) - 'a']--;
    }

    for (int count: record) {
        if (count != 0) {               // record数组如果有的元素不为零0，说明字符串s和t 一定是谁多了字符或者谁少了字符。
            return false;
        }
    }
    return true;                        // record数组所有元素都为零0，说明字符串s和t是字母异位词
}
```

### 349. 两个数组的交集

给定两个数组 `nums1` 和 `nums2` ，返回 *它们的交集* 。输出结果中的每个元素一定是 **唯一** 的。我们可以 **不考虑输出结果的顺序** 。

这道题，解法很简单，但是代码写的我很难受，很丑陋。

后来发现题目限定了数据范围，用数组来做哈希，才写的稍微好看了点

```java
public int[] intersection(int[] nums1, int[] nums2) {
    boolean[] Occured1 = new boolean[1001];
    boolean[] Occured2 = new boolean[1001];
    LinkedList<Integer> set = new LinkedList<>();
    for (int i : nums1) {
        Occured1[i] = true;
    }
    for (int i : nums2) {
        Occured2[i] = true;
    }
    for (int i = 0; i < Occured1.length; i++) {
        if (Occured1[i] && Occured2[i]) {
            set.addLast(i);
        }
    }
    int[] r = new int[set.size()];
    int i = 0;
    for (Integer integer : set) {
        r[i] = integer;
        i ++;
    }
    return r;
}
```

### 202. 快乐数

题目中说了会 **无限循环**，那么也就是说**求和的过程中，sum会重复出现，这对解题很重要！**

所以这道题目使用哈希集法，来判断这个sum是否重复出现，如果重复了就是return false， 否则一直找到sum为1为止。

稍微值得注意一下的就是那个被遗忘了很久的小技巧：利用取模运算来得到一个十进制数上的各个位。

```java
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        while (true) {
            n = getSum(n);
            if (!set.add(n)) {
                return false;
            }
            if (n == 1) {
                return true;
            }
        }
    }
    private int getSum(int n) {
        int Sum = 0;
        while (n != 0) {
            Sum += (n % 10) * (n % 10);
            n = n / 10;
        }
        return Sum;
    }
```

### 1.两数之和

感觉解法比较容易忘记，多多复习。



### 454.四数相加II

这个的思路和两数之和比较类似，但是不是完全相同，也需要多看几次

### 383.赎金信

和242是类似的。没什么好说的，解法也是借鉴了242的思路

```java
    public boolean canConstruct(String ransomNote, String magazine) {
        char[] chars = ransomNote.toCharArray();
        char[] chars1 = magazine.toCharArray();
        int[] CharFrequency = new int[26];
        for (char c : chars1) {
            CharFrequency[c - 'a'] ++;
        }
        for (char aChar : chars) {
            CharFrequency[aChar - 'a'] --;
        }
        for (int i : CharFrequency) {
            if (i < 0) {
                return false;
            }
        }
        return true;
    }
```

### 15.三数之和

## 字符串

### 344.反转字符串

重拳出击

### 541. 反转字符串II

同样重拳出击。把前一个反转字符串的函数稍微改改参数，改成一个通用的，然后用在这道题里。

前一道题的解就是`reverseString(char[] s, 0, s.length - 1)`

```java
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        int CompleteReverse = chars.length / (2 * k);
        int Remaining = chars.length % (2 * k);
        for (int i = 0; i < CompleteReverse; i++) {
            int Start = i * 2 * k;
            reverseString(chars, Start, Start + k - 1);
        }
        if (Remaining < k && Remaining > 0) {
            reverseString(chars, chars.length - Remaining, chars.length - 1);
        }
        else if (Remaining >= k && Remaining < 2 * k){
            reverseString(chars, CompleteReverse * 2 * k, CompleteReverse * 2 * k + k - 1);
        }
        return String.valueOf(chars);
    }
    private void reverseString(char[] s, int Start, int End) {
        int L = Start, R = End;
        while (L != R) {
            if ((R - L) == 1) {
                char tmp = s[L];
                s[L] = s[R];
                s[R] = tmp;
                break;
            }
            char tmp = s[L];
            s[L] = s[R];
            s[R] = tmp;
            L ++;
            R --;
        }
    }
```

### 剑指Offer 05.替换空格

常规的解法，空间复杂度和时间复杂度都是O(N)

```java
    public String replaceSpace(String s) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == ' ') {
                stringBuilder.append("%20");
            }else {
                stringBuilder.append(aChar);
            }
        }
        return stringBuilder.toString();
    }
```

如果想把这道题目做到极致，就不要只用额外的辅助空间了！

### 151.翻转字符串里的单词

这道题我觉得值得提一嘴的就是我自己利用双指针写的那个，找到每个单词的函数。

它用于找到字符串`s`中的每个单词，并将其放入一个List，返回。

这道题就把List里的东西按逆序拿出来然后拼接一下就行了。

```java
    private  List<String> RemoveSpace(String s) {
        char[] chars = s.toCharArray();
        ArrayList<String> strings = new ArrayList<>();
        int Start = 0, End = 0;
        while (End < chars.length) {
            if (chars[Start] != ' ') {
                while (End < chars.length  && chars[End] != ' ') {
                    End ++;
                }
                strings.add(String.valueOf(chars, Start, End - Start));
                Start = End;
            }else {
                Start ++;
                End ++;
            }
        }
        return strings;
    }
```

### 剑指 Offer 58 - II. 左旋转字符串

使用额外空间的解法很简单，时间复杂度和空间复杂度都是O(N)。

<img src="https://i.imgur.com/IRpfrw6.png" alt="image-20230831142103934" style="zoom: 80%;" />

```java
public String reverseLeftWords(String s, int n) {
    char[] chars = s.toCharArray();
    char[] ret = new char[s.length()];
    int index = ret.length - n;
    for (int i = 0; i < n; i++) {
        ret[index] = chars[i];
        index ++;
    }
    index = 0;
    for (int i = n; i < chars.length; i++) {
        ret[index] = chars[i];
        index ++;
    }
    return String.valueOf(ret);
}
```

原地反转;

1. 反转区间为前n的子串
2. 反转区间为n到末尾的子串
3. 反转整个字符串

在541已经写过了一个通用的反转函数。

结果还跟前面那个差不多

### 28. 实现 `strStr()`[KMP]

KMP算法：给定两个字符串S和P，要求找到P在S中第一次出现的起始位置。

首先定义两个东西：

- 主串S：主串中包含模式串，我们要从主串里面寻找模式串第一次出现的位置。
- 模式串P：模式串是我们要从主串里寻找出现位置的串。

然后再强调一下Next数组是给谁计算的：Next数组是在模式串P上计算的，是给P计算的，不是给S计算的。

然后再强调一下前缀和后缀：

- 前缀不包含最后一个字母，因为这个前缀是字符串本身。
- 后缀也不能包含第一个字母，因为这样也是字符串本身。
- 不管前缀还是后缀，其顺序都是从左往右读。举个例子，"abacc"，前缀"ab"，后缀"acc"而不是`cca`。



我们记模式串P的Char数组长度为N，模式串P是Char数组P。那么Next数组的长度也是N。

接下来定义Next数组中的每个数值的意义：

- 对于`Next[i]`，其意义是以`P[i]`结尾的子串中，最长的相同前后缀的长度。
  - 以`P[i]`结尾的子串，它的前缀的集合，与后缀的集合，的交集中，长度最长的那个元素。
  - 对于子串"aba"，它的前缀集合为{"a", "ab"}，后缀 集合为{"ba", "a"}。两个集合的交集为{"a"}，那么长度最长的元素就是字符串"a"了，长度为1



然后接下来解释，怎么用这个Next数组：

如下图，假定主串S和模式串P，在主串的第`i`位，模式串的第`j`位，失去了匹配。

这就意味着我们可以推断出一件事来：

- P在左闭右开区间`[0,j)`之间的部分（我们记作Q），和S在左闭右开区间`[i-j, i)`之间的子串部分（我们记作R），是相等的子串。
- 然后我们通过查询`Next[j-1]`，又可以知道Q的最长公共前后缀的长度是多少，在图中我们以序号1和序号2来表示。
- 又因为R和Q是相等的串，所以在R中，序号4和序号3，也是相同的前后缀。

那么我们就会想，为什么不直接把序号1的部分往右平移一下，和序号3的部分对齐一下，让你从序号1的**后面一个字符**再开始匹配呢？

这样对比暴力匹配的算法，直接跳过了很多根本不可能的比较尝试，所以自然加速了。

通过平移之后的图，我们可以很自然的推断，`i`是不用变的，**但是`j`是需要变化为`j'`的**。而这个`j'`的值应该是多少呢？

- `j'`的值，毫无疑问就是序号1的长度**减1再加1**（因为索引从0开始，序号1最后一个字符的下标是其长度-1），而序号1的长度，就是`Next[j-1]`。
  - 这也是为什么有一部分Next数组，会整体+1，因为这样`j' = Next[j-1]`，而不是`j' = Next[j-1] + 1`（不需要多加一个1）。

<img src="https://i.imgur.com/nQ0uTrV.jpg" alt="2b299c4459c0b796b301fd150a4c88c" style="zoom:50%;" />

所以我们很自然的，就把KMP算法的主体写出来了：

```java
int i = 0; 
int j = 0;

while (i < s.length() && j <  p.length()) {
    if (j == -1 || t[i] == p[j]) {
        i++;
        j++;
    }
    else {
        j = next[j - 1];
    } 
if (j == p.length())
   return i - j;
else 
   return -1;
```

当然，这个主体里还有一些小Bug，**如果j == 0就没匹配上怎么办？那 next[j - 1]不存在。**所以我们要添加一个额外的特殊判断：在第0个字符就失去匹配的情况下，j肯定还是从0开始，**而i则要`i ++`**（如果`i`不往右挪一个的话，那情况还是没有改变，还是第0个失去匹配（`i = j = 0`），死循环）。

所以主体是：

```java
public int strStr(String s, String Patten) {
    if (Patten == null || s == null) {
        return -1;
    }
    if ("".equals(Patten)) {
        return 0;
    }
    int[] Next = getNext(Patten);
    int i = 0, j = 0;
    char[] chars = s.toCharArray();
    char[] chars1 = Patten.toCharArray();
    while (i < s.length() && j < Patten.length()) {
        if (chars[i] == chars1[j]) {
            i ++;
            j ++;
        }else {
            // 这里做一个特殊情况判断,防止数组越界。是第0个字符就失去匹配的情况
            if (j == 0) {
                j = 0;
                // i ++指向主串第1个字符 ( i 不 ++ 的话还是第0个字符失去匹配,回到这里 i = j = 0 的情,陷入死循环)
                i ++;
            }
            else {
                j = Next[j - 1];
            }
        }
    }
    if (j == Patten.length()) {
        return i-j;
    }
    return -1;
}
```

那么接下来再介绍一下，如何快速求解Next数组：

最简单的办法：暴力法，利用Next数组的定义。

```java
private int[] getNext(String Patten) {
    int[] Next = new int[Patten.length()];
    for (int i = 0; i < Next.length; i++) {
        if (i == 0) {
            Next[0] = 0;
        }
        else {
            for (int j = 0; j < i; j++) {
                CharSequence charSequence = Patten.subSequence(0, j + 1);
                String substring = Patten.substring(i - j, i + 1);
                if (charSequence.equals(substring)) {
                    Next[i] = j + 1;
                }
            }
        }
    }
    return Next;
}
```

在引出快速求Next数组的方法之前，先引出Next数组的完整的定义。

- 定义 “k-前缀” 为一个字符串的前 k 个字符。“k-后缀” 为一个字符串的后 k 个字符。k 必须小于字符串长度。
- next[x] 定义为： P[0]~P[x] 这一段字符串，使得**k-前缀恰等于k-后缀**的最大的k。

这个定义中，不知不觉地就包含了一个匹配——前缀和后缀相等。接下来，我们考虑采用递推的方式求出`next`数组。如果next[0], next[1], ... next[x-1]均已知，那么如何求出 `next[x]` 呢？



因为我们已经知道`Next[x - 1]`（我们记作`n`），我们把`P[0]`到`P[x]`可以分成几个部分：

- 序号1：长度为`n`的P[0]到P[n-1] 
-  P[n]本身 
- 剩余部分（未知的部分）
- 序号2：长度为`n`的P[x-n-2]到P[x-1]
- P[x]本身

<img src="https://i.imgur.com/gQGW8h9.jpg" alt="270e9fd052d025d56c57ba48dfe5744" style="zoom: 50%;" />

序号1的部分和序号2的部分，是`P[x-1]`最长的相等前后缀。

所以，如果`P[n] == P[x]`，那么`P[x]`最长的相等前后缀就直接在`P[x-1]`的基础上各自向后扩展一位即可，也即：

```java
Next[x] = n + 1
```

但是，如果如果`P[n] != P[x]`呢？

因为`P[n] != P[x]`，但是`x`又不能动，所以我们只能动`n`，而n又只能往左边动（缩小），所以我们需要**缩短这个n**，把它改成小一点的值，再来试试 P[x] 是否等于 P[n]。

又因为，序号1和序号2，分别是`P[x-1]`最长的相等前、后缀，所以对于`P[x-1]`的任意一个前缀，与之相等的后缀，必然落在序号2中，而前缀本身则必然落在序号1中。

也就是说，序号1的K-前缀，必然等于序号2的K-后缀。

也就是说，假如我们逐个前缀逐个前缀地回退n（**不是逐个字符逐个字符地**），当回退到某个k-前缀，使得`p[k] = p[x]`时：

- 我们在序号1中找到由`P[0]`到`P[k-1]`所构成的前缀
- 然后我们可以在序号2中，找到以`P[x-1]`结尾的，同样的后缀。
- 我们给前缀加上`P[k]`，给后缀加上`P[x]`，新得到的前后缀仍然相等，**他们也就是**`P[x]`的最长相等前后缀。



又因为序号1和序号2**是完全一样**的串，所以序号1和序号2的后缀又是一样的。

所以，我们要求的东西，其实是——序号1的前缀和后缀的公共长度。

- 而这个值，就是`Next[n-1]`
- 这里同样有一个边界情况，那就是`n == 0`，这种情况其实就是意味着n已经不能再往左边移了，我们没有找到那个`P[n] = P[x]`，也就意味着，`P[x]`的公共前后缀最长长度为0。

```java

```

玉玉了，总有两个测试用例过不去。还找不到Bug。

求Next还是用暴力定义法吧。

### 459.重复的子字符串

判断字符串s是否由重复子串组成，只要两个s拼接在一起，里面还出现一个s的话，就说明是由重复子串组成。

当然，我们在判断 s + s 拼接的字符串里是否出现一个s的的时候，**要刨除 s + s 的首字符和尾字符**，这样避免在s+s中搜索出原来的s，我们要搜索的是中间拼接出来的s。

证明其充分性如下：

命题：对于字符串S，我们用两个S首尾拼接起来组成S+S，对于S+S，如果其中间有一个子串S，那么S就是一个符合提议的字符串。

我们假定字符串S由n个子串s重复构成，即S = sssssss...sss。

那么假如我们将2个S拼接在一起，得到的就是一个由2n个s组成的字符串：sssss...ssssss....sssssss。我们记作S + S

由于s至少要重复两次，因此n是大于等于2的，那么2n大于等于4。

所以假如我们把S+S一头一尾的s去掉，剩余2n-2个s。这2n-2个s，至少可以组成一个S。

- 因为2n - 2>=n，解得就是n >= 2，这是充要条件。

所以我们可以得出结论，假如S是符合题意的字符串，那么S + S里至少会在中间出现一个S。

```java
public boolean repeatedSubstringPattern(String s) {
    String s2 = s + s;
    return  s2.substring(1, s2.length() - 1).contains(s);
}
```

暴力法：

假定一个长度为N的字符串S由n个s构成，S = ssssss...ssss：

- N一定是n的倍数，所以我们需要穷举的n少于N个。
- 又因为s至少要重复一次，所以n不会大于N的一半。
- 对于任意的$i \in [n, N)$，有$S[i] = S[i - n]$。n是周期嘛。函数的周期性。

因此我们可以从小到大枚举n，然后进行第三条的判断。

```java
public boolean repeatedSubstringPattern(String s) {
    char[] chars = s.toCharArray();
    for (int i = 1; i * 2 <= s.length() ; i++) {
        if (s.length() % i == 0) {
            boolean match = true;
            for (int j = i; j < s.length();  j++) {
                if (chars[j] != chars[j - i]) {
                    match = false;
                }
            }
            if (match) {
                return true;
            }
        }
    }
    return false;
}
```

## 双指针

### 27.移除元素

这个复习一下双指针：

- 快指针：寻找新数组的元素 ，新数组就是不含有目标元素的数组
- 慢指针：指向更新 新数组下标的位置

快指针是逐步向前，什么都不管，慢指针只有在它指向的位置填了一个值以后，才往前移动一个位置。

<img src="https://code-thinking.cdn.bcebos.com/gifs/27.%E7%A7%BB%E9%99%A4%E5%85%83%E7%B4%A0-%E5%8F%8C%E6%8C%87%E9%92%88%E6%B3%95.gif" alt="27.移除元素-双指针法" style="zoom: 80%;" />

### 344.反转字符串

稍微复习一下代码

### 剑指Offer 05.替换空格

### 151.翻转字符串里的单词

复习一下双指针去除空格的代码

### **三数之和四数之和都还没做**

## 栈与队列

### 232. 用栈实现队列

这里是用栈来实现队列。栈只有栈顶一个出入口，而队列一头是入口另外一头是出口。

题目的要求是，用两个栈来实现一个先入先出的队列。**只能使用标准的栈操作** -- 也就是只有 push , pop  size, peek, 和 is empty 操作是合法的。

两个栈，一个输入栈一个输出栈。

- 进入队列的时候，直接加入输入栈。
- 离开队列的时候，从输出栈弹出栈顶的那个。如果输出栈是空的，先把输入栈的东西全部弹出到输出栈，然后再弹出。

稍微举个例子就知道，从输入栈弹出，入栈到输出栈，最后从输出栈弹出的时候的顺序，和加入队列时的顺序是一样的。

可以这么理解：栈是用于翻转出栈顺序的，翻转两次，把顺序翻转回来了，出栈顺序和入栈顺序就一样了。

举个例子：按照1,2,3,4的顺序进入输入栈，弹出顺序是4,3,2,1，而4321也是进入输出栈的顺序，那么从输出栈弹出的时候，顺序就反转回了原来的1234。

### 225. 用队列实现栈

要强调一下，是单向队列。

请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（`push`、`top`、`pop` 和 `empty`）。

两种思路：

- 第一种，使用两个队列Q1和Q2。
  - 入栈的时候进入（push）Q1。
  - 出栈的时候，先把Q1除了最后一个元素以外的，全部转移（pop）到Q2，然后再把Q1剩下的的出队列。然后把Q2的元素都pop回Q1
- 第二种：只有一个队列Q
  - 入栈的时候：Push到Q
  - 出栈的时候：把除了最后一个元素的，都Pop然后Push到Q，这样最后一个元素就从队尾到了队头，然后Pop一下，对外表现出来就是栈顶元素出栈。

这里需要指明一下的是，Pop和Top的实现不能完全一样。否则先Top后Pop的操作，Top会把Pop所做的改变全部回退回去。

正确的实现：

```java
public int pop() {
    int size = queue.size();
    while (size > 1) {
        queue.add(queue.poll());
        size --;
    }
    return queue.poll();
}

public int top() {
    // 先弹出栈顶元素
    int pop = pop();
    // 然后再加回去
    push(pop);
    return pop;
}
```

错误的top实现：这样假如先Top再Pop，或者反过来，后一个操作都会把前一个操作所做的更改回滚回去。

```java
public int pop() {
    int size = queue.size();
    while (size > 1) {
        queue.add(queue.poll());
        size --;
    }
    return queue.peek();
}
```

### 20. 有效的括号

这道题是用栈。但是写代码的技巧让代码变简单了很多：在匹配左括号的时候，右括号先入栈，就只需要比较当前元素和栈顶相不相等就可以了，比左括号先入栈代码实现要简单的多了！

遇到左括号的时候，让它对应的右括号入栈，而不是入栈它本身。这样在碰到右括号的时候，只需要检查当前和栈顶是否相等。

<img src="https://code-thinking.cdn.bcebos.com/gifs/20.%E6%9C%89%E6%95%88%E6%8B%AC%E5%8F%B7.gif" alt="20.有效括号" style="zoom:80%;" />

```java
public boolean isValid(String s) {
    // 奇数个括号是肯定不可能能匹配得上的。匹配的括号都是成对出现的
    if (s.length() % 2 != 0) {
        return false;
    }
    char[] chars = s.toCharArray();
    LinkedList<Character> stack = new LinkedList<>();
    for (int i = 0; i < chars.length; i++) {
        if (chars[i] == '[') {
            stack.addLast(']');
        }
        else if (chars[i] == '(') {
            stack.addLast(')');
        }
        else if (chars[i] == '{') {
            stack.addLast('}');
        }else {
            // 假如是一个正确的串,Stack里必然有一个与 chars[i] 相同的 ']'或 ')' 或'}所以如果Stack为空，直接return false
            if (stack.isEmpty()) {
                return false;
            }
            if (chars[i] == ']') {
                // 因为removeLast在链表为空的情况下会抛出异常，所以假如Stack是空，那么肯定是 return false
                Character character = stack.removeLast();
                if (character != ']') {
                    return false;
                }
            }
            else if (chars[i] == ')') {
                Character character = stack.removeLast();
                if (character != ')') {
                    return false;
                }
            }
            else if (chars[i] == '}') {
                Character character = stack.removeLast();
                if (character != '}') {
                    return false;
                }
            }
        }
    }
    return stack.isEmpty();
}
```

### 1047. 删除字符串中的所有相邻重复项

给出由小写字母组成的字符串 `S`，**重复项删除操作**会选择两个相邻且相同的字母，并删除它们。

在 S 上反复执行重复项删除操作，直到无法继续删除。

在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。

**示例：**

> 输入："abbaca"
> 输出："ca"
> 解释：
> 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。

 

我们在删除相邻重复项的时候，其实就是要知道当前遍历的这个元素，我们在前一位是不是遍历过一样数值的元素，那么如何记录前面遍历过的元素呢？

所以就是用栈来存放，那么栈的目的，就是存放遍历过的元素，当遍历当前的这个元素的时候，去栈里看一下我们是不是遍历过相同数值的相邻元素。

然后再去做对应的消除操作。 如动画所示：

看动画就很简单，只不过如果之前没做过的话，很难想得到这种类似脑筋急转弯的题目。

<img src="https://code-thinking.cdn.bcebos.com/gifs/1047.%E5%88%A0%E9%99%A4%E5%AD%97%E7%AC%A6%E4%B8%B2%E4%B8%AD%E7%9A%84%E6%89%80%E6%9C%89%E7%9B%B8%E9%82%BB%E9%87%8D%E5%A4%8D%E9%A1%B9.gif" alt="1047.删除字符串中的所有相邻重复项" style="zoom:80%;" />

### 150. 逆波兰表达式求值

给你一个字符串数组 `tokens` ，表示一个根据 [逆波兰表示法](https://baike.baidu.com/item/逆波兰式/128437) 表示的算术表达式。

请你计算该表达式。返回一个表示表达式值的整数。

**注意：**

- 有效的算符为 `'+'`、`'-'`、`'*'` 和 `'/'` 。
- 每个操作数（运算对象）都可以是一个整数或者另一个表达式。
- 两个整数之间的除法总是 **向零截断** 。
- 表达式中不含除零运算。
- 输入是一个根据逆波兰表示法表示的算术表达式。
- 答案及所有中间计算结果可以用 **32 位** 整数表示。

**逆波兰表达式：**

逆波兰表达式是一种后缀表达式，所谓后缀就是指算符写在后面。

- 平常使用的算式则是一种中缀表达式，如 `( 1 + 2 ) * ( 3 + 4 )` 。
- 该算式的逆波兰表达式写法为 `( ( 1 2 + ) ( 3 4 + ) * )` 。

逆波兰表达式主要有以下两个优点：

- 去掉括号后表达式无歧义，上式即便写成 `1 2 + 3 4 + * `也可以依据次序计算出正确结果。
- 适合用栈操作运算：遇到数字则入栈；遇到算符则取出栈顶两个数字进行计算，并将结果压入栈中



按照题干的最后一句话做就行了。

### 239. 滑动窗口最大值（**单调队列**,非常逆天）

这是一道Hard题，暴力法只能过41 / 51 个测试用例。

暴力法就是穷举每个窗口，然后遍历每个窗口，找到其最大值。





这道题涉及到单调队列：所谓的单调队列，就是保证队列里的元素一定要是单调递增或者单调递减的。

举个例子来说明单调队列的实现：

1. 2入列
2. 3入列，因为我们要保证队列的单独递减，所以2不能在3的左边，所以把2弹出。
3. 5入列，为了保证队列的单调性，3弹出
4. 1入列，不需要弹出队头元素即可维护单调性
5. 4入列，把1从入口弹出，4进去，以维护单调性

因此，设计单调队列的时候，pop，和push操作要保持如下规则：

1. pop(value)：如果窗口移除的元素value等于单调队列的出口元素，那么队列弹出元素，否则不用任何操作
2. push(value)：如果push的元素value大于入口元素的数值，那么就将队列入口的元素弹出，直到push元素的数值小于等于队列入口元素的数值为止

保持如上规则，每次窗口移动的时候，只要问que.front()就可以返回当前窗口的最大值。

<img src="https://code-thinking.cdn.bcebos.com/gifs/239.%E6%BB%91%E5%8A%A8%E7%AA%97%E5%8F%A3%E6%9C%80%E5%A4%A7%E5%80%BC.gif" alt="239.滑动窗口最大值" style="zoom:80%;" />



我们需要一个队列，这个队列呢，放进去窗口里的元素，然后随着窗口的移动，队列也一进一出，每次移动之后，队列告诉我们里面的最大值是什么。

### 347. 前 K 个高频元素（堆，优先队列）

这道题标准解法：

1. 用Map统计各个元素的频率
2. 把全部的`MapEntry<K,V>`插入一个最大堆
3. 连续移除堆顶的最大元素K次，得到的就是前K个高频元素

另外一种办法是利用最小堆：

1. 先往最小堆里插入K个`MapEntry`。
2. 在后续插入的过程中，保持堆的Size始终为K。这也就意味着，新插入一个，就要从堆顶移除一个。并且需要注意，**先插入后移除**（因为插入的可能恰好就是新的最小值）
3. 最后剩下的K个就是前K个高频的元素

## 二叉树

### 94/144/145：二叉树的深度优先遍历

### 使用迭代的深度优先遍历：重点复习中序和后序

前序遍历：

前序遍历是中左右，每次先处理的是中间节点。所以先将根节点放入栈中，出栈，然后将右孩子加入栈，再加入左孩子。

为什么要先加入 右孩子，再加入左孩子呢？ 因为这样出栈的时候才是先左后右的顺序。

<img src="https://code-thinking.cdn.bcebos.com/gifs/%E4%BA%8C%E5%8F%89%E6%A0%91%E5%89%8D%E5%BA%8F%E9%81%8D%E5%8E%86%EF%BC%88%E8%BF%AD%E4%BB%A3%E6%B3%95%EF%BC%89.gif" alt="二叉树前序遍历（迭代法）" style="zoom:80%;" />

注意一下，`NULL`的孩子是不加入栈中的。

```java
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> ret = new ArrayList<>();
        Deque<TreeNode> treeNodes = new ArrayDeque<>();
        treeNodes.addLast(root);
        while (!treeNodes.isEmpty()) {
            TreeNode treeNode = treeNodes.removeLast();
            if (treeNode.right != null) {
                treeNodes.addLast(treeNode.right);
            }
            if (treeNode.left != null) {
                treeNodes.addLast(treeNode.left);
            }
            ret.add(treeNode.val);
        }
        return ret;
    }
```

**没办法把前序遍历的代码稍微改改就应用于中序遍历**：

这个代码确实需要仔细记一下:

- 遍历结束的条件是，我们当前访问的节点为NULL，且栈已经空了。

```java
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        TreeNode current = root;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.addLast(current);
                current = current.left; // 一直向左走到底
            }
            else {
                current = stack.removeLast();// 从栈里弹出的数据，就是要处理的数据 
                arrayList.add(current.val); // 中
                current = current.right; // 去处理右子树
            }
        }
        return arrayList;
    }
```

 前序遍历的代码调整一下入栈顺序，即可得到后序遍历的代码。可以这么记：前的反义词是后，所以前序的代码翻转一下左右子节点的入栈顺序，然后翻转一下结果，就可以得到后序遍历。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20200808200338924.png" alt="前序到后序" style="zoom:80%;" />

### 102.层序遍历

Leetcode的层序遍历的要求要高一点，要求我们保证每时每刻队列里存储的都是同一层次的节点。

这也就意味着我们要一层一层地处理。而我们使用了size来巧妙地做到了这一点。

在内层的while循环做了两件事：

- 把本层的值依次，从左往右，全部踢出队列并做想做的处理。
- 同时每次踢出的时候，都把被踢出的这个节点的下一层的子节点加入队列。

这样当内层while循环结束的时候，本层的节点全部处理完毕，队列里则保存了下一层的全部节点，并且是按照从左往右的顺序保存的。

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) {
        return result;
    }
    Deque<TreeNode> NodeQueue = new LinkedList<>();
    NodeQueue.addLast(root);
    while (!NodeQueue.isEmpty()) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int size = NodeQueue.size();
        
        // 这里的内层while循环是值得关注的地方
        while (size > 0) {
            TreeNode treeNode = NodeQueue.removeFirst();
            if (treeNode.left != null) {
                NodeQueue.addLast(treeNode.left);
            }
            if (treeNode.right != null) {
                NodeQueue.addLast(treeNode.right);
            }
            arrayList.add(treeNode.val);
            size --;
        }
        result.add(arrayList);
    }
    return result;
}
```

### 107. 二叉树的层序遍历 II

给你二叉树的根节点 `root` ，返回其节点值 **自底向上的层序遍历** 。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）



就是把102层序遍历的结果倒过来输出一遍

先按照正常的层序遍历走一遍出结果，然后把结果按层翻转一下再`return`。

### 199.二叉树的右视图

给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20210203151307377.png" alt="199.二叉树的右视图" style="zoom:80%;" />

其实就是每一层最右边的那个值，也就是层序遍历代码的内层循环，size == 1的时候剩下的唯一的那个节点。把它加入结果List即可。

关键代码：

```java
Deque<TreeNode> NodeQueue = new ArrayDeque<>();
NodeQueue.addLast(root);
while (!NodeQueue.isEmpty()) {
    int size = NodeQueue.size();
    while (size > 0) {
        TreeNode treeNode = NodeQueue.removeFirst();
        if (treeNode.left != null) {
            NodeQueue.addLast(treeNode.left);
        }
        if (treeNode.right != null) {
            NodeQueue.addLast(treeNode.right);
        }
        if (size == 1) {
            arrayList.add(treeNode.val);
        }
        size --;
}
```

### 637.二叉树的层平均值

给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。

就是把内层循环里处理的时候求和，退出循环的时候求个平均值然后再放入结果数组

<!--往下是2023/9/4日-->

### 429.N叉树的层序遍历

就是把下面的关键代码，换成遍历子节点List，添加非空的到队列里而已。

```java
        TreeNode treeNode = NodeQueue.removeFirst();
        if (treeNode.left != null) {
            NodeQueue.addLast(treeNode.left);
        }
        if (treeNode.right != null) {
            NodeQueue.addLast(treeNode.right);
        }
```

### 116. 填充每个节点的下一个右侧节点指针

这道题需要注意的地方，就是不能省略掉对Left和Right指针的非空判断。**永远要记得，队列里只放非空的子节点，所以在Add之前要先判空**。

而这里强调了给定的树是完全二叉树，所以判空只需要判断Left。

下一道题则没有强调这个，所以需要对Left和Right分别判空并Add到队列里去

```java
        while (!NodeQueue.isEmpty()) {
            int size = NodeQueue.size();
            if (size == 1) {
                Node node = NodeQueue.removeFirst();
                node.next = null;
                if (node.left != null) {
                    // 此处不判空的话会触发因为加入null导致的空指针异常
                    NodeQueue.addLast(node.left);
                    NodeQueue.addLast(node.right);
                }
            }else {
                Node Prev = NodeQueue.removeFirst();
                size --;
                while (size > 0) {
                    if (Prev.left != null && Prev.right != null) {
                        NodeQueue.add(Prev.left);
                        NodeQueue.add(Prev.right);
                    }
                    Node Next = NodeQueue.removeFirst();
                    Prev.next = Next;
                    Prev = Next;
                    size --;
                }
                if (Prev.left != null && Prev.right != null) {
                    NodeQueue.add(Prev.left);
                    NodeQueue.add(Prev.right);
                }
            }
```

### 117.填充每个节点的下一个右侧节点指针II

只不过给的不是完全二叉树罢了

Left指针和Right指针分别判空，然后再Add到Queue即可。

### 104. 二叉树的最大深度

最大深度就是二叉树的层数，所以我们可以用层序遍历。每次处理一层的时候深度就加一。

关键代码：反正都是层序遍历魔改。

```java
int Depth = 0;
while (!NodeQueue.isEmpty()) {
    Depth ++;
    int size = NodeQueue.size();
    while (size > 0) {
        TreeNode treeNode = NodeQueue.removeFirst();
        if (treeNode.left != null) {
            NodeQueue.addLast(treeNode.left);
        }
        if (treeNode.right != null) {
            NodeQueue.addLast(treeNode.right);
        }
        size --;
    }
}
```

当然这道题其实也可以用递归，一棵树的最大深度，就是：它左子树的最大深度和它右子树的最大深度的较大者，再加1。

迭代1毫秒，递归0毫秒。

```java
public int maxDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    int LeftDepth = maxDepth(root.left);
    int RightDepth = maxDepth(root.right);
    return Math.max(LeftDepth, RightDepth) + 1;
}
```

### 111.二叉树的最小深度

和前一道题的层序遍历思路是一样的，只不过在这里，当遇到第一个Left和Right都为空的节点的时候，提前return即可。

```java
int MinDepth = 0;
while (!NodeQueue.isEmpty()) {
    MinDepth ++;
    int size = NodeQueue.size();
    while (size > 0) {
        TreeNode treeNode = NodeQueue.removeFirst();
        if (treeNode.left != null) {
            NodeQueue.addLast(treeNode.left);
        }
        if (treeNode.right != null) {
            NodeQueue.addLast(treeNode.right);
        }
        // 提前return即可
        if (treeNode.left == null && treeNode.right == null) {
            return MinDepth;
        }
        size --;
    }
}
```

不能直接仿照104的递归来做，需要稍微改一下。

完全按照那个逻辑的话，不太适用，语言不好描述，可以照下面的图和代码模拟一下，得不到正确答案，因为没排除空节点的影响。

<img src="https://i.imgur.com/2q6yruK.png" alt="image-20230904162536184" style="zoom: 80%;" />

```java
public int minDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    int i = minDepth(root.left);
    int i1 = minDepth(root.right);
    return Math.min(i, i1) + 1;
}
```

排除空节点的影响的递归：因为空子树的深度肯定是0，所以走非空的另外一边，才能找到叶子结点。

```java
public int minDepth(TreeNode root) {
    if (root == null) return 0;
    int left = minDepth(root.left);
    int right = minDepth(root.right);
    if (left == 0 || right == 0) return Math.max(left, right) + 1;
    return Math.min(left, right) + 1;
}
```

层序遍历的方法1毫秒，递归9毫秒。

### 101. 对称二叉树

给你一个二叉树的根节点 `root` ， 检查它是否轴对称。



这里和其他常见的递归不太一样，其他的递归都是去处理每个节点。**但是这里的比较，是以整棵树为单位进行比较的**。

所以我们要明确递归函数Compare的语义：比较两棵树Left和Right是否互为镜像。



两棵树Left和Right互为镜像的条件是：抛去Left或者Right之一为NULL的情况，这是递归的边界情况。

1. `Left.val == Right.val`
2. Left的左子树和Right的右子树也要互为镜像
3. Left的右子树和Right的左子树也要互为镜像

三个条件取`AND`，同时满足。2和3则是Compare递归发生的地方。

这里常见的误区是，把轴对称推论为——左右两棵子树是一模一样的树。

但是实际上，观察下图就知道，应该是左右两棵子树互为镜像。

<img src="https://assets.leetcode.com/uploads/2021/02/19/symtree1.jpg" alt="img" style="zoom:80%;" />

```java
public boolean isSymmetric(TreeNode root) {
    if (root == null) {
        return true;
    }
    else {
        return Compare(root.left, root.right);
    }
}
private boolean Compare(TreeNode left, TreeNode right) {
    if (left == null && right == null) {
        return true;
    }
    if (left == null && right != null) {
        return false;
    }
    if (left != null && right == null) {
        return false;
    }
    return (left.val == right.val) && Compare(left.left, right.right)  && Compare(left.right, right.left);
}
```

### 559. N 叉树的最大深度（**递归的有点意思**）

跟二叉树的层序遍历一样，只不过改一点关键代码。见429

代码就不贴了，无非就是用一个Depth遍历维护层数，从顶层到底层的层数就是最大深度。

利用层序的方法2毫秒，只超过了百分之25的。



而递归的也可以模仿104。只不过这里由“从左右子树的深度里选最大的再加一”，变成——“从全部的多棵子树的深度里选最大的再加一”

递归直接0毫秒 beats 100%

递归处的思路也很简单，逐个求出子树的最大深度，并于当前已知的子树最大深度值做比较。

```java
public int maxDepth(Node root) {
    if (root == null) {
        return 0;
    }
    int MaxChildDepth = 0;
    for (Node child : root.children) {
        MaxChildDepth = Math.max(MaxChildDepth, maxDepth(child));
    }
    return MaxChildDepth + 1;
}
```

### 222. 完全二叉树的节点个数（**中等，要求使用完全BST的性质**）

任何一棵二叉树的节点个数都可以使用下面的递归来求：

一棵给定树的节点数 = 左子树的节点数 + 右子树的节点数 + 1(根节点本身)。

```java
public int countNodes(TreeNode root) {
    if (root == null) {
        return 0;
    }
    return countNodes(root.left) + countNodes(root.right) + 1;
}
```

也可以通过各种其他的遍历来求，比如迭代的层序遍历，一个一个的统计即可。

**但是这些解法都没有使用上完全二叉树的性质**，这道题被列为中等难度的题，显然就是不希望我们使用这些方法。

**进阶：**遍历树来统计节点是一种时间复杂度为 `O(n)` 的简单解决方案。你可以设计一个更快的算法吗？

更快的算法的复杂度是$O({(logN)}^2)$的，对数的平方级别的。快于线性时间复杂度。

但是我现在不想看了，我想开摆

<!--以下是2023/09/05-->

### 110.平衡二叉树

这里首先要区分一下高度和深度

- 一个节点的高度：指从它出发，**向下**到叶结点的，最长简单路径边的条数。
- 一个节点的深度：指从它出发，**向上**到根节点的，最长简单路径边的条数

深度是距离根节点的距离，高度是距离叶节点的距离。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20210203155515650.png" alt="110.平衡二叉树2" style="zoom: 44%;" />

这里的高度平衡二叉树定义为：

> 一个二叉树*每个节点* 的左右两个子树的高度差的绝对值不超过 1 。



然后再提一下，为什么求深度用前序遍历，而求高度用后序遍历：

前序遍历是从深度浅的到深度深的，而后序遍历是从高度矮的到高度高的。

> - **前序遍历（中左右）**：前序遍历是一种从树根开始，按照中-左-右的顺序依次访问节点的遍历方式。这意味着你会首先访问根节点，然后是左子树，然后是右子树。因此，在前序遍历中，你首先访问根节点，然后依次访问深度较小的节点。因此，前序遍历适合计算节点的深度，因为你可以从上到下依次访问节点。
> - **后序遍历（左右中）**：后序遍历是一种从树根开始，按照左-右-中的顺序依次访问节点的遍历方式。这意味着你会首先访问左子树，然后是右子树，最后是根节点。因此，在后序遍历中，你首先访问深度较大的节点，然后逐渐向上访问深度较小的节点。因此，后序遍历适合计算节点的高度，因为你可以从下到上依次访问节点。



回到这道题：

既然这道题对平衡二叉树的定义涉及到高度，那么我们必然要从递归求取二叉树的左子树和右子树的高度入手。

这里我们需要分情况讨论，并不能简单的直接左右子树高度的较大者再加一的方式递归。

**因为左右子树可能就是不平衡的，这种情况下再求高度也没有意义，因为子树不平衡的话父树肯定不平衡。**



1. 首先明确递归的语义：递归函数`getHeight(TreeNode root)`，求取以`root`为根结点的树的高度，并且假如root不是平衡树，返回-1表示不是平衡树。
2. 递归的第一步：明确边界情况——自然是`root == null`的时候`return 0`。
3. 因为求高度是后序遍历。那么先对左子树，然后右子树，递归调用`getHeight`。
4. 假如子树求取的高度得到了-1，那么`root`也必然不平衡，返回-1。
5. 假如子树求取的高度都是正数，**但两棵子树都是平衡的，不代表`root`就是平衡的，所以还要保证两棵子树的高度差不超过1。**如果超过，返回-1。
6. 否则，两棵子树的高度我们都知道，取其中较大者再加一，即是`root`的高度。

那么如何判断`root`是否是平衡的呢？只要其求取的高度不等于-1就是平衡的。

```java
public boolean isBalanced(TreeNode root) {
    return getHeight(root) >= 0;
}
private int getHeight(TreeNode root) {
    if (root == null) {
        return 0;
    }
    int Left_height = getHeight(root.left);
    int Right_height = getHeight(root.right);
    if (Left_height < 0 || Right_height < 0 || Math.abs(Right_height -Left_height) > 1) {
        return -1;
    }
    return Math.max(Left_height, Right_height) + 1;
}
```

### 257. **二叉树的所有路径**

回溯递归，多复习。做得少

### 404. 左叶子之和

给定二叉树的根节点 `root` ，返回所有左叶子之和。

左叶子，就是叶子结点，并且它是它父节点的Left节点。

我的思路：递归遍历，找到全部的左叶子的节点的值，得到值的List之后遍历List求和。

左叶子没办法从它自身判断，只能从它的父节点root判断，判断的条件是：

```java
if (root.left.left == null && root.left.right == null) {
}
```

```java
public int sumOfLeftLeaves(TreeNode root) {
    LinkedList<Integer> list = new LinkedList<>();
    getLeftLeaves(root, list);
    int sum = 0;
    for (Integer integer : list) {
        sum += integer;
    }
    return sum;
}
private void getLeftLeaves(TreeNode root, List<Integer> list) {
    if (root != null) {
        if (root.left != null) {
            if (root.left.left == null && root.left.right == null) {
                list.add(root.left.val);
            }
        }
        getLeftLeaves(root.left, list);
        getLeftLeaves(root.right, list);
    }
}
```

当然这道题也可以直接层序遍历，迭代地去做，对于每个遍历到的节点，检查其左子节点是否是叶子结点，把叶子结点的值放入List。

```java
private void getLeaves_LevelOrder(TreeNode root, List<Integer> list) {
        if (root != null) {
            Deque<TreeNode> nodeQueue = new ArrayDeque<>();
            nodeQueue.addLast(root);
            while (!nodeQueue.isEmpty()) {
                TreeNode treeNode = nodeQueue.removeFirst();
                if (treeNode.left != null){
                    // 如果左子节点存在,加入队列
                    nodeQueue.addLast(treeNode.left);
                    TreeNode left = treeNode.left;
                    // 如果左子节点是叶子结点,把其值也加入list
                    if (left.left == null && left.right == null) {
                        list.add(left.val);
                    }
                }
                if (treeNode.right != null) {
                    nodeQueue.addLast(treeNode.right);
                }
            }
        }
}
```

### 513. 找树左下角的值

给定一个二叉树的 **根节点** `root`，请找出该二叉树的 **最底层 最左边** 节点的值。

使用层序遍历显然很好做，只需要找到最后一层，然后返回其最左边的节点即可。

1. 第一遍层序遍历得到层数
2. 第二遍遍历找到最底层，返回队列头部的节点的值即可。

或者每层都从右到左遍历，这样最后遍历到的节点就是最底层最左边的节点。



层序1毫秒，DFS递归0毫秒。

因为递归的时候是先对左子树进行DFS的，所以在递归调用的下一层，`if (Depth > depth)`在左子树的时候会进入，但是右子树的时候因为`Depth == depth`，所以不会进入，所以不会改变`result`的值为同一层的右叶子节点的值。

使用 DFS 进行树的遍历，每次优先 DFS 当前节点的左子树，每次第一次搜索到当前深度 depth 时，必然是当前深度的最左节点，此时用当前节点值来更新 `result`。

```java
class Solution {
    int depth;
    int result;
    public int findBottomLeftValue(TreeNode root) {
        dfs(root, 1);
        return result;
    }
    private void dfs(TreeNode root, int Depth) {
        if (root == null) {
            return;
        }
        if (Depth > depth) {
            depth = Depth;
            result = root.val;
        }
        dfs(root.left, Depth + 1);
        dfs(root.right, Depth + 1);
    }
}
```

### 112. **路径总和**

给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。

又是被简单题网暴的一天。

层序遍历的做法：

- 引入两个队列，一个队列用于一层一层地存储树节点
- 另外一个队列用于存储这一层的每个节点，从根节点到它的和，与上一个队列一一对应。
- 每层都依次检查可能存在的叶子节点，看看其对应的总和是否能和目标值对上号。

```java
public boolean PathSum_LeverOrder(TreeNode root, int targetSum) {
    if (root == null) {
        return false;
    }
    Deque<TreeNode> nodeQueue = new ArrayDeque<>();
    Deque<Integer> SumQueue = new ArrayDeque<>();
    nodeQueue.addLast(root);
    SumQueue.addLast(root.val);
    while (!nodeQueue.isEmpty()) {
        int size = nodeQueue.size();
        while (size > 0) {
            TreeNode treeNode = nodeQueue.removeFirst();
            int integer = SumQueue.removeFirst();
            if (treeNode.left == null && treeNode.right == null && integer == targetSum) {
                return true;
            }
            if (treeNode.left != null) {
                // 把子节点和子节点对应的目标和放进去
                nodeQueue.addLast(treeNode.left);
                SumQueue.addLast(treeNode.left.val + integer);
            }
            if (treeNode.right != null) {
                nodeQueue.addLast(treeNode.right);
                SumQueue.addLast(treeNode.right.val + integer);
            }
            size --;
        }
    }
    return false;
}
```



然后是递归的做法，我的代码写的非常的垃圾，有一大堆的CornerCase。

```java
public boolean PathSumHelper(TreeNode root, int targetSum) {
    if (root == null) {
        return false;
    }
    if (root.left == null && root.right == null && targetSum == root.val) {
        return true;
    }
    if (root.left == null && root.right == null && targetSum != 0) {
        return false;
    }
    else {
        boolean b = false,r = false;
        if (root.left != null) {
            b = PathSumHelper(root.left, targetSum - root.val);
        }
        if (root.right != null) {
            r =  PathSumHelper(root.right, targetSum - root.val);
        }
        return b || r;
    }
}
```

官方题解的代码：

观察要求我们完成的函数，我们可以归纳出它的功能：询问是否存在从当前节点 root 到叶子节点的路径，满足其路径和为 sum。

假定从根节点到当前节点的值之和为 val，我们可以将这个大问题转化为一个小问题：是否存在从当前节点的子节点到叶子的路径，满足其路径和为 sum - val。

不难发现这满足递归的性质，若当前节点就是叶子节点，那么我们直接判断 sum 是否等于 val 即可（因为路径和已经确定，就是当前节点的值，我们只需要判断该路径和是否满足条件）。若当前节点不是叶子节点，我们只需要递归地询问它的子节点是否能满足条件即可。

```java
public boolean hasPathSum_Recursive(TreeNode root, int targetSum) {
    if (root == null) {
        return false;
    }
    if (root.left == null && root.right == null) {
        return root.val == targetSum;
    }
    return hasPathSum_Recursive(root.left, targetSum - root.val) || hasPathSum_Recursive(root.right, targetSum - root.val);
}
```

<!--以下是2023/09/06-->

### 106.从中序与后序遍历序列构造二叉树

给定两个整数数组 `inorder` 和 `postorder` ，其中 `inorder` 是二叉树的中序遍历， `postorder` 是同一棵树的后序遍历，请你构造并返回这颗二叉树。

举个例子来说明一下思路：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]

因为中序遍历是左==>中==>右，后序遍历是左==>右==>中。

1. 首先我们可以从后序遍历找到中，因为一整棵左子树的全部的值，和一整棵右子树的全部的值，都在中节点的前面，所以中间根节点的值肯定是最后一个。因此，根节点是3。
2. 那么我们就知道，在中序遍历中，左子树==>3==>右子树。因此我们利用3将`inorder`数组分割为左右两部分：左子树`[9]`，和右子树的`[15 20  7]`。同时把PostOrder也分割成`[9]`和 `[15 7 20]`两部分。 
3. 然后递归的，我们对左子树和右子树重复123步。
4. 对右子树：根据postorder `15,7,20`，可知在右子树中，20是根节点，然后回到中序`15,20,7`易知左子树是15，右子树是7。
5. 对左子树：根据postorder `[9]`，可知左子树的根节点也是9，也是唯一一个节点，这是递归结束的地方。



我自己写的，10毫秒：`buildChildTree`的语义：根据传入的中序和后序数组构建出这棵子树。

那么递归的语义就是：

1. 本棵子树的根节点是后序的最后一个数值。根据这个构造出根节点，并分割出其左右子树的中序遍历数组，以及后序遍历数组。
2. 对其左子树调用`buildChildTree`，传入前一步分割出的左子树的中序和后序遍历数组
3. 对右子树也递归调用`buildChildTree`。
4. 本棵子树的Left指针等于2步的返回值，Right指针等于3步的返回值。

概括一点说就是：本棵子树等于：递归构建左子树，递归构建右子树，根节点的Left是左子树递归的返回值，右子树同理。

当然，我这里的耗时比较久，是因为每次递归都复制了数组四次，所以耗时比较久，但是可以把复制数组改为传递数组的起始和终止索引，这样会快一点。

```java
public TreeNode buildTree(int[] inorder, int[] postorder) {
    return buildChildTree(inorder, postorder);
}
private TreeNode buildChildTree(int[] inorder, int[] postorder) {
    if (postorder.length == 0) {
        return null;
    }
    else {
        int rootVal = postorder[postorder.length - 1];
        TreeNode rootNode = new TreeNode(rootVal);
        int i = 0;
        for (; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                break;
            }
        }
        int[] LeftInorder = new int[i];
        int[] RightInorder = new int[inorder.length - LeftInorder.length - 1];
        System.arraycopy(inorder, 0,LeftInorder, 0,LeftInorder.length);
        System.arraycopy(inorder, i + 1,RightInorder, 0,RightInorder.length);
        int L = 0, R = 0;
        for (; L < postorder.length; L ++) {
            boolean find = false;
            for (int i1 : LeftInorder) {
                if (i1 == postorder[L]) {
                    find = true;
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        for (; R < postorder.length; R ++) {
            boolean find = false;
            for (int i1 : RightInorder) {
                if (i1 == postorder[R]) {
                    find = true;
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        int[] LeftPostOrder = new int[LeftInorder.length];
        int[] RightPostOrder = new int[RightInorder.length];
        System.arraycopy(postorder, L, LeftPostOrder, 0, LeftPostOrder.length);
        System.arraycopy(postorder, R, RightPostOrder, 0, RightPostOrder.length);
        rootNode.left = buildChildTree(LeftInorder, LeftPostOrder);
        rootNode.right = buildChildTree(RightInorder, RightPostOrder);
        return rootNode;
    }
```

按照优化的再写一版：仅仅进步了1毫秒罢了。仅仅进步1毫秒的原因是，耗时的大头——遍历post分割左右子树的后序数组，是一个二重循环。

可以通过一个Map<值，值的索引>来加速对根节点在中序数组的索引的查找。

这个二重循环，也可以优化：

并且我在写这些代码的时候还遗漏掉了重要的一点——后序数组的构成是：左子树+右子树 + 一个根节点。并且左子树的后序遍历数组和中序遍历数组的长度是一样的，我们记作L。右子树同理记作R。也就是说，从`0`到`L-1`就是左子树的后序，`L`到`L + R-1`就是右子树的后序遍历，**所以大可不必用二重循环来分割后序数组。**

```java
private TreeNode buildChildTree_optimized(int[] inorder, int inStart, int inEnd, int[] postorder, int PoStart, int PoEnd) {
    if (PoStart > PoEnd) {
        return null;
    }
    else {
        int rootVal = postorder[PoEnd];
        TreeNode rootNode = new TreeNode(rootVal);
        int i = inStart; // i是rootVal在前序里的索引
        for (; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                break;
            }
        }
        // 左子树的中序inStart ==> i -1.右子树是 i + 1 ==> inEnd
        int L = PoStart, R = PoStart;
        for (; L <= PoEnd ; L ++) {
            boolean find = false;
            for (int i1 = inStart; i1 < i; i1 ++) {
                if (inorder[i1] == postorder[L]) {
                    find = true;
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        for (; R <=PoEnd; R ++) {
            boolean find = false;
            for (int i1 = i + 1; i1 <= inEnd; i1 ++) {
                if (inorder[i1] == postorder[R]) {
                    find = true;
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        // 左子树的后序:L ==> L + i - inStart - 1.右子树是 R ==> R + inEnd - i - 1
        rootNode.left = buildChildTree_optimized(inorder, inStart, i - 1, postorder, L, L + i - inStart - 1);
        rootNode.right = buildChildTree_optimized(inorder, i + 1, inEnd, postorder, R, R + inEnd - i - 1);
        return rootNode;
    }
```

不管怎么说，框架都是：

- 第一步：如果数组大小为零的话，说明是空节点了。
- 第二步：如果不为空，那么取后序数组最后一个元素作为节点元素。
- 第三步：找到后序数组最后一个元素在中序数组的位置，作为切割点
- 第四步：切割中序数组，切成中序左数组和中序右数组 （顺序别搞反了，一定是先切中序数组）
- 第五步：切割后序数组，切成后序左数组和后序右数组
- 第六步：递归处理左区间和右区间

难点就是如何切割，以及在切割的过程中，**保持区间左右端点的开闭语义的一致性**（就像二分查找）。

更优化的版本就不在这里写了，就在下一题写吧。

### 105.从前序与中序遍历序列构造二叉树

给定两个整数数组 `preorder` 和 `inorder` ，其中 `preorder` 是二叉树的**先序遍历**， `inorder` 是同一棵树的**中序遍历**，请构造二叉树并返回其根节点。



先序遍历是根节点==>整棵左子树==>整棵右子树，所以`preorder`的第0个元素是根节点。然后根据根节点，把中序遍历一分为二——左子树的中序遍历和右子树的中序遍历。



所以，前序加中序，或者后序加中序，都可以构造出原始的二叉树，**但是前序加后序没办法，因为二者的功能重叠了——都只能找到根节点，但是找不到左子树和右子树**。

递归的框架和106是一致的：

1. 如果数组大小为0的话，那么说明是空节点
2. 如果不为空，那么取前序数组的第0个元素作为根节点root。
3. 利用根节点的值，在中序数组作为切割点，将中序数组一分为二——左子树的中序遍历和右子树的中序遍历。
4. 利用第3步得到的两个数组，将后序遍历的数组也切割成两部分——左子树的后序遍历和右子树的先序遍历。
5. 递归处理左子树L和右子树R。
6. `root.Left = L,root.Right = R`
7. `return root`

我自己写的，1毫秒。前一道题106也可以通过同样的代码优化到这个水平。

```java
HashMap<Integer, Integer> ValToIndex;
public TreeNode buildTree(int[] preorder, int[] inorder) {
    ValToIndex = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        ValToIndex.put(inorder[i], i);
    }
    return buildTreeHelper(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
}
private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
    if (preStart > preEnd) {
        return null;
    }
    else {
        int rootVal = preorder[preStart];
        int rootIndex = ValToIndex.get(rootVal);
        TreeNode root = new TreeNode(rootVal);
        int LeftLen = (rootIndex - 1) - inStart + 1;
        int RightLen = inEnd - (rootIndex + 1) + 1;
        /*
        中序遍历:左 中 右
        前序遍历 中 左 右
        左子树的中序: inStart ==> rootIndex - 1.长度是 rootIndex - inStart
        右子树的中序: rootIndex + 1 ==>inEnd
        左子树的前序:preStart + 1 ==> LeftLen + preStart = rootIndex - inStart + preStart;
        设结尾为x: x - (preStart + 1) + 1 = LeftLen
        右子树的前序: LeftLen + preStart + 1 ==> LeftLen + preStart + RightLen = ;
        设结尾为y: y - (LeftLen + preStart + 1) + 1 = RightLen
        */
        root.left = buildTreeHelper(preorder, inorder, preStart + 1, LeftLen + preStart, inStart, rootIndex - 1);
        root.right = buildTreeHelper(preorder, inorder, LeftLen + preStart + 1, LeftLen + preStart + RightLen, rootIndex + 1, inEnd);
        return root;
    }
}
```

### 654. 最大二叉树

很简单的，按照题意递归就好了

需要注意的点都写在了注释里了

2毫秒一遍过

```java
public TreeNode constructMaximumBinaryTree(int[] nums) {
    return constructMaximumBinaryTreeHelper(nums, 0, nums.length - 1);
}
private TreeNode constructMaximumBinaryTreeHelper(int[] nums, int Start, int End) {
    if (Start > End) {
        return null;
    }
    else {
        int MaxIndex = Start;  //不要写MaxIndex = 0;
        for (int i = Start; i <= End; i++) {
            if (nums[i] > nums[MaxIndex]) {
                MaxIndex = i;
            }
        }
        TreeNode root = new TreeNode(nums[MaxIndex]);
        root.left = constructMaximumBinaryTreeHelper(nums, Start, MaxIndex - 1);
        root.right = constructMaximumBinaryTreeHelper(nums, MaxIndex + 1, End); //End这里不要写nums.length - 1
        return root;
    }
```

### 617. 合并二叉树

给你两棵二叉树： `root1` 和 `root2` 。

想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，**不为** null 的节点将直接作为新二叉树的节点。

按照题意递归即可。

```java
public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) {
        return null;
    }
    else if (root1 != null && root2 == null) {
        return root1;
    }
    else if (root1 == null) {
        return root2;
    }
    else {
        TreeNode treeNode = new TreeNode(root1.val + root2.val);
        treeNode.left = mergeTrees(root1.left, root2.left);
        treeNode.right = mergeTrees(root1.right, root2.right);
        return treeNode;
    }
}
```

### 700. 二叉搜索树中的搜索

属于是考数据结构了

给定二叉搜索树（BST）的根节点 `root` 和一个整数值 `val`。

你需要在 BST 中找到节点值等于 `val` 的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 `null` 。

```java
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) {
        return null;
    }
    if (val < root.val) {
        return searchBST(root.left, val);
    }
    if (val > root.val) {
        return searchBST(root.right, val);
    }
    return root;
}
```



###  98.验证二叉搜索树（递归的有意思）

像下面这样递归是不行的：举个例子，因为它没办法排除：[5,4,6,null,null,3,7]。3比5小，但是在5的右子树上。

```java
if (root == null) {
    return true;
}
if (root.left != null && root.right != null) {
    return root.val > root.left.val && root.val < root.right.val && isValidBST(root.left) && isValidBST(root.right) ;
}
else if (root.left != null && root.right == null){
    return root.val > root.left.val && isValidBST(root.left);
}else if (root.left == null && root.right != null){
    return root.val < root.right.val && isValidBST(root.right) ;
} else {
    return true;
}
```

很容易想到的一种方法是，先中序遍历，因为一棵合法的BST的中序遍历，其结果肯定是严格递增的，我们只需要验证这一点即可。

```java
public boolean isValidBST(TreeNode root) {
    ArrayList<Integer> arrayList = new ArrayList<>();
    getInOrder(root, arrayList);
    Object[] objects = arrayList.toArray();
    for (int i = 0; i < objects.length - 1; i++) {
        int pre = (Integer)objects[i];
        int next = (Integer)objects[i + 1];
        if (pre >= next) {
            return false;
        }
    }
    return true;
}
private void getInOrder(TreeNode root, List<Integer> list) {
    if (root == null) {
        return;
    }
    getInOrder(root.left, list);
          list.add(root.val);
    getInOrder(root.right, list);

}
```

官解：

设计一个递归函数 helper(root, lower, upper) 来递归判断，函数表示考虑以 root 为根的子树，判断子树中所有节点的值是否都在 (l,r)(l,r)(l,r) 的范围内（注意是开区间）。如果 root 节点的值 val 不在 (l,r)(l,r)(l,r) 的范围内说明不满足条件直接返回，否则我们要继续递归调用检查它的左右子树是否满足，如果都满足才说明这是一棵二叉搜索树。

那么根据二叉搜索树的性质，在递归调用左子树时，我们需要把上界 upper 改为 root.val，即调用 helper(root.left, lower, root.val)，因为左子树里所有节点的值均小于它的根节点的值。同理递归调用右子树时，我们需要把下界 lower 改为 root.val，即调用 helper(root.right, root.val, upper)。

函数递归调用的入口为 helper(root, -inf, +inf)， inf 表示一个无穷大的值。

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }
}
```

### 530.二叉搜索树的最小绝对差

我的思路：中序遍历。因为树是一棵BST，所以中序遍历得到的序列是严格递增的序列，序列里的数字的顺序和它们在数轴上的顺序是一样的。

而绝对值的几何意义就是数轴上的距离。而距离最小的，只可能出现在相邻的两个点之间的距离上。

```java
public int getMinimumDifference(TreeNode root) {
    ArrayList<Integer> arrayList = new ArrayList<>();
    getList(root, arrayList);
    int ret = Integer.MAX_VALUE;
    for (int i = 0; i < arrayList.size() - 1; i++) {
        int abs = Math.abs(arrayList.get(i + 1) - arrayList.get(i));
        if (abs < ret) {
            ret = abs;
        }
    }
    return ret;
}
private void getList(TreeNode root, List<Integer> list) {
    if (root != null) {
        getList(root.left, list);
        list.add(root.val);
        getList(root.right, list);
    }
}
```

这个可以优化到空间复杂度O(1)，只需要用一个变量来记录上一个被遍历到的值，然后跟本次被遍历到的值去比较，因为按照前序的情况下，上一次的值和本次的值在数轴上就是相邻的两个点。

```java
int MinVal;
int PrevVal;
public int getMinimumDifference(TreeNode root) {
    MinVal = Integer.MAX_VALUE;
    PrevVal = -1;
    Helper(root);
    return MinVal;
}
private void  Helper(TreeNode root) {
    if (root == null) {
        return;
    }
    Helper(root.left);
    if (PrevVal == -1) {
        PrevVal = root.val;
    }
    else {
        MinVal = Math.min(MinVal, root.val - PrevVal);
        PrevVal = root.val;
    }
    Helper(root.right);
}
```

### 501. 二叉搜索树中的众数

给你一个含重复值的二叉搜索树（BST）的根节点 `root` ，找出并返回 BST 中的所有 [众数](https://baike.baidu.com/item/众数/44796)（即，出现频率最高的元素）。

如果树中有不止一个众数，可以按 **任意顺序** 返回。

假定 BST 满足如下定义：

- 结点左子树中所含节点的值 **小于等于** 当前节点的值
- 结点右子树中所含节点的值 **大于等于** 当前节点的值
- 左子树和右子树都是二叉搜索树



我的思路：中序遍历 + 统计出现频率。朴素的做法是先不管哪种顺序，遍历一遍得到全部的数的数组，然后遍历一遍数组，找到频率的最大值，然后输出众数。

然而就算是边中序遍历边统计频率，还是很慢



优化过后的做法：

考虑到BST的中序遍历是递增的，并且**重复出现的数字一定是一个连续出现的段**。所以我们可以顺序扫描中序遍历序列，用  base 记录当前的数字，用 count 记录当前数字重复的次数，用 maxCount 来维护已经扫描过的数当中出现最多的那个数字的出现次数，用 answer 数组记录出现的众数。

并且，我们不存储这个中序遍历，而是一边遍历一边统计。

首先更新 base 和 count:

- 如果该元素和 base 相等，那么 count 自增1；
- 否则将 base 更新为当前数字， **count 复位为 1**。

然后更新 maxCount：

- 如果  Countcount=maxCount，那么说明当前的这个数字 base 出现的次数等于当前众数出现的次数，将 base 加入answer 数组；
- 如果 count>maxCount，那么说明当前的这个数base出现的次数大于当前众数出现的次数，因此，我们需要将maxCount 更新为 count，并**清空 answer 数组后将 base 加入 answer 数组**。

```java
int base;
int count;
int MaxFreq;
Set<Integer> modes;
public int[] findMode(TreeNode root) {
    count = 0;
    base = Integer.MAX_VALUE;
    modes = new HashSet<>();
    findModeHelper(root);
    Object[] objects = modes.toArray();
    int[] ret = new int[objects.length];
    for (int i = 0; i < objects.length; i++) {
        ret[i] = (int) objects[i];
    }
    return ret;
}
private void findModeHelper(TreeNode root) {
    if (root != null) {
        findModeHelper(root.left);
        if (base == Integer.MAX_VALUE) {
            base = root.val;
        }
        if (root.val == base) {
            count ++;
        }else {
            base = root.val;
            count = 1;
        }
        if (count == MaxFreq) {
            modes.add(root.val);
        }
        if (count > MaxFreq) {
            MaxFreq = count;
            modes.clear();
            modes.add(root.val);
        }
        findModeHelper(root.right);
    }
}
```

### 236. 二叉树的最近公共祖先（递归法不会）

给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（**一个节点也可以是它自己的祖先**）。





迭代的很简单：

1. 遍历一遍整棵树，把每个节点的父子关系映射存入一个HashMap
2. 从 p 节点开始不断往它的祖先移动，并用一个HashSet S记录已经访问过的祖先节点。
3. 同样，我们再从 q 节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是 p 和 q 的深度最深的公共祖先，即 LCA 节点。
   - 在这里，我们把q的祖先也不断加入S，答案就是第一个加入集合失败的节点（或者说第一个`QPath.contains(qAncestor)`返回true的节点）。



### 701.二叉搜索树中的插入操作

```java
public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) {
        return new TreeNode(val);
    }
    if (val > root.val) {
        // 往右子树插,同时把root.right更新为插入了val之后的结果。
        root.right = insertIntoBST(root.right, val);
    }
    if (val < root.val) {
        // 同理
        root.left = insertIntoBST(root.left, val);
    }
    return root;
}
```

### 450. 删除二叉搜索树中的节点

![image-20230907171944507](https://i.imgur.com/jSaNg2s.png)

```java
public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) {
        return null;
    }
    if (key < root.val) {
        root.left = deleteNode(root.left, key);
        return root;
    }
    else if (key > root.val) {
        root.right = deleteNode(root.right, key);
        return root;
    }
    else {
        if (root.left == null && root.right == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        if (root.right == null) {
            return root.left;
        }
        TreeNode MostLeft = root.right;
        while (MostLeft.left != null) {
            MostLeft = MostLeft.left;
        }
        root.right = deleteNode(root.right, MostLeft.val);
        MostLeft.left = root.left;
        MostLeft.right = root.right;
        return MostLeft;
    }
}
```

### 108. 将有序数组转换为二叉搜索树

给你一个整数数组 `nums` ，其中元素已经按 **升序** 排列，请你将其转换为一棵 **高度平衡** 二叉搜索树。

每次取中间节点作为根节点，然后把数组一分为二，左边就是左子树，右边就是右子树，对左半边数组和右半边数组递归构造即可。

永远注意——区间端点值的开闭要保持一致性，我习惯于全部取闭区间。

```java
public TreeNode sortedArrayToBST(int[] nums) {
    return buildTree(nums, 0, nums.length - 1);
}

private TreeNode buildTree(int[] nums,int Star, int End) {
    if (Star > End) {
        return null;
    }
    else {
        int Mid = (Star + End) / 2;
        int rootVal = nums[Mid];
        TreeNode treeNode = new TreeNode(rootVal);
        treeNode.left = buildTree(nums, Star, Mid - 1);
        treeNode.right = buildTree(nums, Mid + 1, End);
        return treeNode;
    }
}
```

### 538.把二叉搜索树转换为累加树

给出二叉 **搜索** 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），使每个节点 `node` 的新值等于原树中大于或等于 `node.val` 的值之和。

提醒一下，二叉搜索树满足下列约束条件：

- 节点的左子树仅包含键 **小于** 节点键的节点。
- 节点的右子树仅包含键 **大于** 节点键的节点。
- 左右子树也必须是二叉搜索树。

~~所以node的新值其实就是其右子树的节点的值的和~~。并不是，举个例子，node在root的左子树的右子树上，那意味着root的右子树上任意一个值都大于node，求和的时候要把右子树全部加上。比如下图，节点2的新值是35，是树中所有大于等于2的值的和。



<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20201023160751832.png" alt="538.把二叉搜索树转换为累加树" style="zoom: 50%;" />

## 回溯

<!--9.9笔试完之后几天在看别的东西,于9.13重新从头开始看回溯,以下是从头开始看的-->

**回溯法解决的问题都可以抽象为树形结构**，是的，我指的是所有回溯法的问题都可以抽象为树形结构！

因为回溯法解决的都是在集合中递归查找子集，**集合的大小就构成了树的宽度，递归的深度，都构成的树的深度**。

递归就要有终止条件，所以必然是一棵高度有限的树（N叉树）。

### 回溯法模板

回溯函数模板返回值以及参数

- 回溯函数起名字为backtracking。
- 回溯算法中函数返回值一般为void。

再来看一下参数，因为回溯算法需要的参数可不像二叉树递归的时候那么容易一次性确定下来，所以一般是先写逻辑，然后需要什么参数，就填什么参数。

```java
void backtracking(参数)
```

回溯函数终止条件

既然是树形结构，那么遍历树形结构一定要有终止条件。

所以回溯也有要终止条件。

什么时候达到了终止条件，树中就可以看出，一般来说搜到叶子节点了，也就找到了满足条件的一条答案，把这个答案存放起来，并结束本层递归。

所以回溯函数终止条件伪代码如下：

```java
if (终止条件) {
    存放结果;
    return;
}
```

回溯搜索的遍历过程:

在上面我们提到了，回溯法一般是在集合中递归搜索，集合的大小构成了树的宽度，递归的深度构成的树的深度。for循环就是遍历集合区间，可以理解一个节点有多少个孩子，这个for循环就执行多少次。

backtracking这里自己调用自己，实现递归。

```java
for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
    处理节点;
    backtracking(路径，选择列表); // 递归
    回溯，撤销处理结果
}
```

可以从图中看出**for循环可以理解是横向遍历，backtracking（递归）就是纵向遍历**，这样就把这棵树全遍历完了，一般来说，搜索叶子节点就是找的其中一个结果了。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20210130173631174.png" alt="回溯算法理论基础" style="zoom: 50%;" />

```java
void backtracking(参数) {
    if (终止条件) {
        存放结果;
        return;
    }

    for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
        处理节点;
        backtracking(路径，选择列表); // 递归
        回溯，撤销处理结果
    }
}

```

### 77. 组合

给定两个整数 `n` 和 `k`，返回范围 `[1, n]` 中所有可能的 `k` 个数的组合。

> 输入：n = 4, k = 2
> 输出：
> [
>   [2,4],
>   [3,4],
>   [2,3],
>   [1,2],
>   [1,3],
>   [1,4],
> ]



需要说明的就是一个东西：在回溯的终止条件处

- 被注释掉的`OneResult.clear()`，Clear会把它清空，回溯的结果不对。举个例子，如下图，比如我们把`[1,2]`放入集合，那么接下来就是回溯，集合里是`[1]`，然后从3,4里选一个，如果调用Clear的话，会直接把集合清空，回溯回去之后结果不对。
- `ResultSet.add(new ArrayList<>(OneResult));`。注意这里是**new**了一个，而不是直接把`OneResult`加入。直接加入的话，因为`OneResult`是复用的，最后`Result`里每个List都指向了同一片内存，结果肯定是重复，错误的。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20201123195407907.png" alt="77.组合3" style="zoom:50%;" />

```java
Deque<Integer> OneResult; //用来保存单次回溯的结果
List<List<Integer>> ResultSet;
public List<List<Integer>> combine(int n, int k) {
    OneResult = new ArrayDeque<>();
    ResultSet = new ArrayList<>();
    BackTracking(n, k, 1);
    return ResultSet;
}
private void BackTracking(int n, int k, int StartIndex) {
    if (OneResult.size() == k) { // 这里是回溯的边界条件，
        ResultSet.add(new ArrayList<>(OneResult));
        // OneResult.clear(); // 这里不要Clear，会把之前几层递归加入的东西也清除掉。
        return;
    }
    for (int i = StartIndex; i <= n - (k - OneResult.size()) + 1 ; i++) {
        OneResult.add(i);
        BackTracking(n, k, i + 1);
        OneResult.remove(i);
    }
}
```

剪枝优化：**如果for循环选择的起始位置之后的元素个数 已经不足 我们需要的元素个数了，那么就没有必要搜索了**。

如下，n = 4 k = 4时。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20210130194335207-20230310134409532.png" alt="77.组合4" style="zoom: 45%;" />

### 216. 组合总和 III

找出所有相加之和为 `n` 的 `k` 个数的组合，且满足下列条件：

- 只使用数字1到9
- 每个数字 **最多使用一次** 

返回 *所有可能的有效组合的列表* 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。



伟大，无需多言。我成功地网暴了Leetcode。

几点需要说明的：

- `Sum`在`if`分支不需要重置为0，因为这样就跟前一道题的Clear一样，会导致回溯之后的结果不正确。举个例子，假如Sum加上了一个2，等于了n，那么在If分支里，不能将Sum置为0。因为当它`return`回溯到被调用的位置的时候，Sum自然会被下面的`Sum -= i`去掉刚才加上的那个2，回溯到加上2之前。**但是如果Sum清零的话，就一切都错了**
- 额外强调一下，除了要回退Path，**还要回退Sum的值**

```java
List<List<Integer>> Result;
Deque<Integer> Path;
int Sum;
public List<List<Integer>> combinationSum3(int k, int n) {
    Result = new ArrayList<>();
    Path = new ArrayDeque<>();
    BackTracking(k, n, 1);
    return Result;
}
public void BackTracking(int k, int n, int StartValue) {
    if (Path.size() == k && Sum == n) {
        Result.add(new ArrayList<>(Path));
        //Sum = 0; //=================>划重点,这里不能把Sum重置为0<================
        return;
    }
    for (int i = StartValue; i < 10; i++) {
        Sum += i;
        Path.add(i);
        BackTracking(k, n, i + 1);
        Path.remove(i);
        Sum -= i; // ===================>划重点,不要忘了回退Sum的值<====================
    }
}
```

剪枝的版本：

1. 假如总和已经超过目标和了，直接return。无需继续往下遍历。
2. 假如Path里的元素个数已经超过限定个数了，直接return。

```java
public void BackTracking(int k, int n, int StartValue) {
    if (Sum > n) {
        return;
    }
    if (Path.size() > k) {
        return;
    }
    if (Path.size() == k && Sum == n) {
        Result.add(new ArrayList<>(Path));
        //Sum = 0;
        return;
    }
    for (int i = StartValue; i < 10; i++) {
        Sum += i;
        Path.add(i);
        BackTracking(k, n, i + 1);
        Path.remove(i);
        Sum -= i;
    }
}
```

### 17.电话号码的字母组合

给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合。答案可以按 **任意顺序** 返回。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

就是老式的九宫格?

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2021/11/09/200px-telephone-keypad2svg.png)

先把回溯函数本体写了：每次回溯一次处理一位数字，也即digits的首位数字。

```java
private void BackTracking(String digits) {
    if (digits.length() == 0) { // 终结情况，当digits的长度是0，就说明每个数字都取了一个对应的字母了，把Path加入结果集
       // 比如"123"，第一次递归处理掉1，传递给下一次递归"23",再下次"3"，第四次就是""了.此时可以把Path加入结果集了
        Result.add(Path.toString());
        return;
    }
    char c = digits.charAt(0);
    int index = Integer.parseInt(String.valueOf(c));
    char[] Options = NumberToChar.get(index);  //找到与这个数字对应的字母的数组
    for (int i = 0; i < Options.length; i++) { // 穷举每一种可能,比如index = 2对应abc，那就穷举取a,b,c时的情况，并递归。
        Path.append(Options[i]);
        BackTracking(digits.substring(1));
        Path.delete(Path.length() - 1, Path.length()); //回溯，换下一种字母选择
    }
}
```

```java
List<String> Result;
StringBuilder Path;
Map<Integer, char[]> NumberToChar;
public List<String> letterCombinations(String digits) {
    if (digits == null) {
        return null;
    }
    if (digits.equals("")) {
        return  new ArrayList<String>();
    }
    NumberToChar = new HashMap<>();
    char[][] chars = new char[][]{
            {'a', 'b', 'c'}, // 2
            {'d', 'e', 'f'}, // 3
            {'g', 'h', 'i'}, // 4
            {'j', 'k', 'l'}, // 5
            {'m', 'n', 'o'}, // 6
            {'p', 'q', 'r', 's'}, // 7
            {'t', 'u', 'v'}, // 8
            {'w', 'x', 'y', 'z'}, // 9
    };
    for (int i = 2; i < 10; i++) {
        NumberToChar.put(i, chars[i - 2]);
    }
    Path = new StringBuilder();
    Result = new ArrayList<>();
    BackTracking(digits);
    return Result;
}
```

<!--以下是2023/09/08-->

### 39. 组合总和

给你一个 **无重复元素** 的整数数组 `candidates` 和一个目标整数 `target` ，找出 `candidates` 中可以使数字和为目标数 `target` 的 所有 **不同组合** ，并以列表形式返回。你可以按 **任意顺序** 返回这些组合。

`candidates` 中的 **同一个** 数字可以 **无限制重复被选取** 。如果至少一个数字的被选数量不同，则两种组合是不同的。 

对于给定的输入，保证和为 `target` 的不同组合数少于 `150` 个。

> 示例 1：
>
> 输入：candidates = [2,3,6,7], target = 7
> 输出：[[2,2,3],[7]]
> 解释：
> 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
> 7 也是一个候选， 7 = 7 。
> 仅有这两种组合。



这道题哥们倒是算是写出来了百分之八十——所有可能的结果都得到了，但是里面有重复的。再加一点去重的工作，就通过了。

但是结果非常的慢，289毫秒，仅击败百分之5。



来看一下别人的解法吧：

```java
private void BackTracking(int[] candidates, int target, int Start) {
    if (Sum > target) {
        return;
    }
    if (target == Sum) {
        Result.add(new ArrayList<>(Path));
        return;
    }
    for (int i = Start; i < candidates.length; i++) {
        Path.add(candidates[i]);
        Sum += candidates[i];
        BackTracking(candidates, target, i);
        Path.remove(candidates[i]);
        Sum -= candidates[i];
    }
}
```



### **40**.组合总和II

给定一个候选人编号的集合 `candidates` 和一个目标数 `target` ，找出 `candidates` 中所有可以使数字和为 `target` 的组合。

`candidates` 中的每个数字在每个组合中只能使用 **一次** 。

用前一道题的去重不行了，会OOM，Leetcode会超时。

因为：

1. 这道题，没有再强调候选人数组里的数字各不相同了。
2. 这道题，限制了一个数字只能用一次。

比如下面的示例1，候选数组里有两个1，但是每个1只能用一次。

如果我们使用之前的回溯算法，就会搜索出两个`[1,7]`的组合

> 示例 1:
>
> 输入: candidates = [10,1,2,7,6,1,5], target = 8,
> 输出:
> [
> [1,1,6],
> [1,2,5],
> [1,7],
> [2,6]
> ]



这道题代码随想录的视频讲得很好，这里要进行的是同一树层的去重，而同一树枝是不需要去重的。

稍微强调几个点：

- 候选人数组在传递给回溯函数之前，**一定要先排序**
- **树层去重的话，需要对数组排序**！
- 去重条件：
  - Used**数组也要进行回溯**

```java
    for (int i = StartIndex; i < candidates.length; i++) {
        if (i > 0 && candidates[i] == candidates[ i - 1] && Used[i - 1] == false) {
            continue;
        }
        Path.add(candidates[i]);
        Used[i] = true;
        Sum += candidates[i];
        BackTracking(candidates, target, i + 1);
        Path.remove(candidates[i]);
        Used[i] = false;
        Sum -= candidates[i];
    }
```

### **131**. 分割回文串

给你一个字符串 `s`，请你将 `s` 分割成一些子串，使每个子串都是 **回文串** 。返回 `s` 所有可能的分割方案。

思路已经清晰，但是要多写几遍。第二遍从头开始的时候已经自己写出来了，思路不好用语言描述，但是多写几遍应该就没问题了。



每次从`StartIndex`到`i`就是我们本层分割的一个子串，假如这个子串是一个回文串的话，那么我们就可以将其加入Path，同时从`i + 1`开始，进入下一次递归，从`i + 1`开始分割下一个回文子串。 

```java
    List<List<String>> Result;
    Deque<String> Path;

    public List<List<String>> partition(String s) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        BackTracking(s, 0);
        return Result;
    }
    private void BackTracking(String s, int StartIndex) {
        if (StartIndex >= s.length()) {
            Result.add(new ArrayList<>(Path));
            return;
        }
        for (int i = StartIndex; i < s.length(); i ++) {
            String substring = s.substring(StartIndex, i + 1);
            if (isPalindrome(substring)) {
                Path.addLast(substring);
                BackTracking(s, i  + 1);
                Path.removeLast();
            }else {
                continue;
            }
        }
    }
    private boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int L = 0, R = chars.length - 1;
        while (L <= R) {
            if (R - L == 1) {
                if (chars[L] != chars[R]) {
                    return false;
                }
            }
            if (chars[L] != chars[R]) {
                return false;
            }
            L ++;
            R --;
        }
        return true;
    }
```



### **93**. 复原 IP 地址

**有效 IP 地址** 正好由四个整数（每个整数位于 `0` 到 `255` 之间组成，且不能含有前导 `0`），整数之间用 `'.'` 分隔。

给定一个只包含数字的字符串 `s` ，用以表示一个 IP 地址，返回所有可能的**有效 IP 地址**，这些地址可以通过在 `s` 中插入 `'.'` 来形成。你 **不能** 重新排序或删除 `s` 中的任何数字。你可以按 **任何** 顺序返回答案。

```java

    StringBuilder stringBuilder;
    List<String> Result;
    int Count;
    public List<String> restoreIpAddresses(String s) {
        Result = new ArrayList<>();
        stringBuilder = new StringBuilder();
        Count = 0; //记录IP地址的段数
        BackTracking(s, 0);
        return Result;
    }
    private void BackTracking(String s, int StartIndex) {
        if (Count == 4) {
            String s1 = stringBuilder.toString();
            if (s1.length() == s.length() + 4) { //不加这一句的话,比如255255255111,会把类似2.5.5.2(后面全丢了)加入结果集。
                Result.add(s1.substring(0, s1.length() - 1));
            }
            return;
        }
        for (int i = StartIndex; i < s.length(); i ++) {
            String substring = s.substring(StartIndex, i + 1);
            if (isValidIP(substring)) {
                stringBuilder.append(substring);
                stringBuilder.append(".");
                Count ++;
                BackTracking(s, i + 1);
                Count --;
                // substring.Length() + 1 = stringBuilder.length() - 1 - x + 1
                stringBuilder.delete(stringBuilder.length() - substring.length() - 1, stringBuilder.length());
            }
        }
    }
    private boolean isValidIP(String s) {
        if (s.length() < 1) {
            return false;
        }
        if (s.length() == 1 && s.charAt(0) == '0') {
            return true;
        }
        if (s.length() > 1 && s.charAt(0) == '0') {
            return false;
        }
        if (s.length() > 3) {
            return false;
        }
        int i = Integer.parseInt(s);
        return i <= 255;
    }
```

### 78. 子集

给你一个整数数组 `nums` ，数组中的元素 **互不相同** 。返回该数组所有可能的子集（幂集）。

解集 **不能** 包含重复的子集。你可以按 **任意顺序** 返回解集。

不需要剪枝，因为我们就是要穷举出所有的子集。

```java
    List<List<Integer>> Result;
    Deque<Integer> Path;
    public List<List<Integer>> subsets(int[] nums) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int num : nums) {
            arrayList.add(num);
        }
        Result.add(arrayList);
         // 逐个列举子集中的元素个数，按照元素个数求子集
        for (int SubSetElementCount = 0; SubSetElementCount < nums.length; SubSetElementCount ++) {
            BackTracking(nums, SubSetElementCount, 0);
        }
        return  Result;
    }
    private void BackTracking(int[] nums, int ElementCount, int StartIndex) {
        if (Path.size() == ElementCount) {
            Result.add(new ArrayList<>(Path));
        }
        for (int i = StartIndex; i < nums.length; i ++) {
            Path.addLast(nums[i]);
            BackTracking(nums, ElementCount, i + 1);
            Path.removeLast();
        }
    }
```

### **90**. 子集 II

给你一个整数数组 `nums` ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。

解集 **不能** 包含重复的子集。返回的解集中，子集可以按 **任意顺序** 排列。

> **示例 1：**
>
> 输入：nums = [1,2,2]
> 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]

1ms。拿前一道题的改改，对Nums排个序，再加个USED数组即可。78和90的关系，就跟之前39/40一样。

```java
List<List<Integer>> Result;
boolean[] Used;
Deque<Integer> Path;
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Used = new boolean[nums.length];
    Result = new ArrayList<>();
    Path = new ArrayDeque<>();
    ArrayList<Integer> arrayList = new ArrayList<>();
    for (int num : nums) {
        arrayList.add(num);
    }
    Result.add(arrayList);
    Arrays.sort(nums);
    // 逐个列举子集中的元素个数，按照元素个数求子集
    for (int SubSetElementCount = 0; SubSetElementCount < nums.length; SubSetElementCount ++) {
        BackTracking(nums, SubSetElementCount, 0);
    }
    return  Result;
}
private void BackTracking(int[] nums, int ElementCount, int StartIndex) {
    if (Path.size() == ElementCount) {
        Result.add(new ArrayList<>(Path));
    }
    for (int i = StartIndex; i < nums.length; i ++) {
        Used[i] = true;
        if (i > 0 && nums[i - 1] == nums[i] && !Used[i - 1] && Used[i]) {
            Used[i] = false;
            continue;
        }else {
            Path.addLast(nums[i]);
            BackTracking(nums, ElementCount, i + 1);
            Path.removeLast();
            Used[i] = false;
        }
    }
}
```

### 491. 递增子序列

给你一个整数数组 `nums` ，找出并返回所有该数组中不同的递增子序列，递增子序列中 **至少有两个元素** 。你可以按 **任意顺序** 返回答案。

数组中可能含有重复元素，如出现两个整数相等，也可以视作递增序列的一种特殊情况。

 **注意这里要求的是递增子序列，意味着我们不能对`nums`排序**、

> 示例 1：
>
> 输入：nums = [4,6,7,7]
> 输出：[[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
> 示例 2：
>
> 输入：nums = [4,4,3,2,1]
> 输出：[[4,4]]

这道题我使用了HashSet去重，32ms。思路与求子集类似。

```java
    List<List<Integer>> Result;
    Deque<Integer> Path;

    HashSet<List<Integer>> Set;
    public List<List<Integer>> findSubsequences(int[] nums) {
        Result = new ArrayList<>();
        Set = new HashSet<>();
        Path = new ArrayDeque<>();
        for (int i = 2; i <= nums.length; i ++) {
            BackTracking(nums, i, 0);
        }
        return Result;
    }

    private void BackTracking(int[] Nums, int ElementCount, int StartIndex) {
        if (Path.size() == ElementCount) {
            if (Set.add(new ArrayList<>(Path))) {
                Result.add(new ArrayList<>(Path));
            }
        }
        for (int i = StartIndex; i < Nums.length; i ++) {
            if (Path.isEmpty() || Nums[i] >= Path.getLast()) {
                Path.addLast(Nums[i]);
                BackTracking(Nums, ElementCount, i + 1);
                Path.removeLast();
            }
        }
    }
```

### 46. 全排列

给定一个不含重复数字的数组 `nums` ，返回其 *所有可能的全排列* 。你可以 **按任意顺序** 返回答案。

注意这里不要再使用`StartIndex`了，**每一层的可选项并不是从`StartIndex`往后的项**，而是上层没使用的项。因为这是一个排列，排列是强调顺序的，相同的元素不同顺序也是一个不同的答案。

```java
List<List<Integer>> Result;
Deque<Integer> Path;
boolean[] Used;
public List<List<Integer>> permute(int[] nums) {
    Result = new ArrayList<>();
    Path = new ArrayDeque<>();
    Used = new boolean[nums.length];
    BackTracking(nums);
    return Result;
}
private void BackTracking(int[] nums) {
    if (Path.size() == nums.length) {
        Result.add(new ArrayList<>(Path));
        return;
    }
    for (int i = 0; i < nums.length; i ++) {
        if (!Used[i]) {
            Used[i] = true;
            Path.addLast(nums[i]);
            BackTracking(nums);
            Path.removeLast();
            Used[i] = false;
        }
    }
}
```

### 47. 全排列 II

给定一个可包含重复数字的序列 `nums` ，***按任意顺序*** 返回所有不重复的全排列。

> **示例 1：**
>
> ```
> 输入：nums = [1,1,2]
> 输出：
> [[1,1,2],
>  [1,2,1],
>  [2,1,1]]
> ```



思路还是排序 + 使用USED数组，跟40题、90题类似。这道题只不过是要在46的基础上微调一下

```java
List<List<Integer>> Result;
Deque<Integer> Path;
boolean[] Used;
public List<List<Integer>> permuteUnique(int[] nums) {
    Result = new ArrayList<>();
    Path = new ArrayDeque<>();
    Used = new boolean[nums.length];
    Arrays.sort(nums);
    BackTracking(nums);
    return Result;
}
private void BackTracking(int[] nums) {
    if (Path.size() == nums.length) {
        Result.add(new ArrayList<>(Path));
        return;
    }
    for (int i = 0; i < nums.length; i ++) {
        if (!Used[i]) { // 这里不要漏掉这个If的条件。
            Used[i] = true;
            if (i > 0 && nums[i - 1] == nums[i] && Used[i - 1] == false && Used[i] == true) {
                Used[i] = false;
                continue;
            } else {
                Path.addLast(nums[i]);
                BackTracking(nums);
                Path.removeLast();
                Used[i] = false;
            }
        }
    }
}
```

### 332.重新安排行程

不想看 头痛

### 51. N 皇后

头太痛了。睡觉了

<!--以下是2023/09/15-->

## 贪心

<!--20230924晚,在结束了动态规划之后, 又从头开始重新看一遍-->

### 455. 分发饼干

一眼贪心，用小的饼干喂饱小的孩子，用大饼干喂饱大孩子。

```java
public int findContentChildren(int[] g, int[] s) {
    //g:孩子的胃口,s:饼干大小
    Arrays.sort(g);
    Arrays.sort(s);
    int Kid = 0, Cookie = 0;
    while (Kid < g.length && Cookie < s.length) {
        if (g[Kid] <= s[Cookie]) {
            Kid ++;
            Cookie ++;
        }else {
            Cookie ++;
        }
    }
    return Kid;
}
```

### **376**. 摆动序列

见代码随想录讲解，这道题细节比较多。

【贪心算法，寻找摆动有细节！| LeetCode：376.摆动序列】 https://www.bilibili.com/video/BV17M411b7NS/?share_source=copy_web&vd_source=1184c486dc565ae483367f8f3e51262e

```java
public int wiggleMaxLength(int[] nums) {
    if (nums.length == 2 && nums[0] != nums[1]) {
        return 2;
    }
    int PreDiff = 0, CurrDiff = 0, res = 1;
    for (int i = 0; i < nums.length - 1; i++) {
        CurrDiff = nums[i + 1] - nums[i];
        if ((PreDiff >= 0 && CurrDiff < 0) || (PreDiff <= 0 && CurrDiff > 0)) {
            res ++;
            PreDiff = CurrDiff;
        }
    }
    return res;
}
```



### 53. 最大子数组和

给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

**子数组** 是数组中的一个连续部分。

暴力法只能过200/210个测试用例。

贪心：当前“连续和”为负数的时候立刻放弃，从下一个元素重新计算“连续和”，因为负数加上下一个元素 “连续和”只会越来越小。

全局最优：选取最大“连续和”

另外一种解法是动态规划，放到动态规划的部分去吧

```java
public int maxSubArray(int[] nums) {
    int sum = nums[0], max = nums[0];
    for (int i = 1; i < nums.length; i++) {
        if (sum >= 0) {
            sum += nums[i];
        }else {
            sum = nums[i];
        }
        max = Math.max(max, sum);
    }
    return max;
}
```



### **122**.买卖股票的最佳时机 II

给你一个整数数组 `prices` ，其中 `prices[i]` 表示某支股票第 `i` 天的价格。

在每一天，你可以决定是否购买和/或出售股票。你在任何时候 **最多** 只能持有 **一股** 股票。你也可以先购买，然后在 **同一天** 出售。

返回 *你能获得的 **最大** 利润* 。



这道题和买卖股票的最佳时机那道题（121）又不太一样，这道题可以进行多次交易。

也就是说可以连续进行多次交易。这也就意味着当我们发现当天价格高于前一天的时候，我们可以在前一天买入然后当天卖出。

因此我们可以遍历价格数组，然后只在前后两天有利润的时候进行一次交易。

```java
public int maxProfit(int[] prices) {
    int MaxProfit = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            MaxProfit += prices[i] - prices[i - 1];
        }
    }
    return MaxProfit;
}
```

### 55. 跳跃游戏

给你一个非负整数数组 `nums` ，你最初位于数组的 **第一个下标** 。数组中的每个元素代表你在该位置可以跳跃的**最大**长度。

判断你是否能够到达最后一个下标，如果可以，返回 `true` ；否则，返回 `false` 。



跳几步无所谓，关键在于可跳的覆盖范围。

不一定非要明确一次究竟跳几步，每次取最大的跳跃步数，这个就是可以跳跃的覆盖范围。

这个范围内，别管是怎么跳的，反正一定可以跳过来。

**那么这个问题就转化为跳跃覆盖范围究竟可不可以覆盖到终点！**

在当前可以到达的覆盖范围内，逐个到达，每到达一个位置，就看看到达的地方可以往前跳几步，如果可到达的范围大于当前的最大范围，那就更新可到达的范围。重复这个步骤，直到覆盖范围盖过了数组的终点，或者最终还是无法覆盖终点。

```java
public boolean canJump(int[] nums) {
    int Cover = nums[0];
    for (int i = 0; i <= Cover; i++) {  // i 每次移动只能在 cover 的范围内移动
        if (i + nums[i] > Cover) {
            Cover = i + nums[i];
        }
        if (Cover >= nums.length - 1) {
            return true; // 如果 cover 大于等于了终点下标，直接 return true 就可以了。
        }
    }
    return false;
}
```

错误的示范：过不去下面这个测试用例，原因Debug一看就明白。因为`i`走出了Cover的范围，i的活动范围只有0到Cover，但是下面的代码，i是可能会越过Cover的范围的，从而得到错误的结论。

> 输入：nums = [3,2,1,0,4]
> 输出：false
> 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。

```java
    public boolean canJump(int[] nums) {
        int CurrentCover = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i + nums[i] > CurrentCover) {
                CurrentCover = i + nums[i];
            }
        }
        return CurrentCover >= nums.length - 1;
    }
```



### 45.跳跃游戏 II

给定一个非负整数数组，你最初位于数组的第一个位置。

数组中的每个元素代表你在该位置可以跳跃的最大长度。

你的目标是使用最少的跳跃次数到达数组的最后一个位置。



每一步都尽可能多走距离，如果覆盖范围还是到不了终点，那就再多走一步，直到覆盖范围到达数组的最后一个位置。





但是这道题我个人其实是用动态规划解决的。因为是先刷完了动态规划的部分然后反过来重新刷贪心的。

```java
public int jump(int[] nums) {
    // dp[i] 代表从0跳转到i的最小跳跃次数
    int[] dp = new int[nums.length];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0; // 一开始起点就在 0 处。不需要跳跃
    // 对于dp[i]: 从 0 到 i - 1的位置依次尝试跳过来。
    // 比如从0 <= j <= i - 1跳过来,那么跳跃次数就是dp[j] + 1。遍历j取能到达i的最小的dp[j] + 1
    for (int i = 1; i < dp.length; i ++) {
        for (int j = 0; j < i; j ++) {
            if (j + nums[j] >= i) { // 如果从 j 可以跳跃到 i。
                dp[i] = Math.min(dp[i], dp[j] + 1);
            }
        }
    }
    return dp[nums.length - 1];
}
```

贪心的做法：

- 在当前可以到达的最远范围内，逐个遍历，找到下一个可以到达的最远范围 `NextMaxCover`。
- 当我们遍历到当前最远范围的末尾，但是还是没到达终点时，我们就多走一步，同时我们刚才已经计算出来下一步可以走过的最远距离，把当前最远范围更新为刚才计算的值。

```java
public int jump(int[] nums) {
    if (nums.length == 1) {
        return 0;
    }
    int jump = 1, NextMaxCover = nums[0], MaxCover = nums[0];
    for (int i = 0; i <= MaxCover; i ++) {
        // 在我们当前可覆盖的范围的基础上,如果我们再多走一步的话,最远可以到达的地方是NextMaxCover
        NextMaxCover = Math.max(NextMaxCover, i + nums[i]);
        if (MaxCover >= nums.length - 1) {
            break;
        }
        if (i == MaxCover) { // 到达当前可覆盖范围的最远处了，但是还是够不到终点,所以得再走一步
            MaxCover = NextMaxCover;
            jump ++;
        }
    }
    return jump;
}
```

### 1005. K 次取反后最大化的数组和

给你一个整数数组 `nums` 和一个整数 `k` ，按以下方法修改该数组：

- 选择某个下标 `i` 并将 `nums[i]` 替换为 `-nums[i]` 。

重复这个过程恰好 `k` 次。可以多次选择同一个下标 `i` 。

以这种方式修改数组后，返回数组 **可能的最大和** 。

> 示例 1：
>
> 输入：nums = [4,2,3], k = 1
> 输出：5
> 解释：选择下标 1 ，nums 变为 [4,-2,3]
>
> 
>
> 示例 3：
>
> 输入：nums = [2,-3,-1,5,-4], k = 2
> 输出：13
> 解释：选择下标 (1, 4) ，nums 变为 [2,3,-1,5,4] 。 

基本思路：

把输入分为两部分：负数和非负。使负数递增排序，非负数递增排序。

- 如果k小于等于负数的个数，那最优解毫无疑问是把全部的负数变为正数，然后求和
- 如果k大于负数的个数，那么我们先把全部的负数变为正数，然后把非负数里较小的先变为负数，这样肯定尽量减少我们损失的数的多少。

但是这样的思路其实有一点小问题，那就是题目说：**可以多次选择同一个下标 `i`**。也就是说我们可以对同一个数连续取反多次。因此这也就会让我们想，如果在我们将负数全部取反之后，剩余的K是一个偶数的话，那么我们可以对同一个正数直接进行偶数次变换，最终的结果就是这个偶数的符号不变，**于是我们就不会有任何的损失**。

举个例子，最开始k = 3。负数只有一个 -1，然后正数有1,2,3。我们完全可以对某个正数连续取2次反，最终正数全部都保持原样。这样的损失是最小的。这样得到的是正确答案，而不是 “先把全部的负数变为正数，然后把非负数里较小的先变为负数”。



而剩余的取反次数为奇数呢？因为奇数可以写成`2n + 1`的形式，我们先对同一个正数变换`2n`次，然后最后一次取反，我们选择让我们损失最小的那个数取反。也就是，最后一次取反，我们可以对最小的正数做，但是也可以对最大的负数做，就看哪一种的损失最小（当然，如果只有正数或者只有负数的时候，我们不需要选，这也是为什么我分三种情况来判断： `Negative.isEmpty()/ NonNegative.isEmpty()/ (!Negative.isEmpty() && !NonNegative.isEmpty())`）。

```java
    public int largestSumAfterKNegations(int[] nums, int k) {
        ArrayList<Integer> Negative = new ArrayList<>();
        ArrayList<Integer> NonNegative = new ArrayList<>();
        for (int num : nums) {
            if (num < 0) {
                Negative.add(num);
            }
            else {
                NonNegative.add(num);
            }
        }
        Negative.sort((o1, o2) -> o1 - o2);
        NonNegative.sort(Comparator.comparingInt(Integer::intValue));
        if (k < Negative.size()) {
            int Sum = 0;
            for (Integer integer : Negative) {
                if (k > 0) {
                    Sum += - integer;
                    k --;
                }
                else {
                    Sum += integer;
                }
            }
            for (Integer integer : NonNegative) {
                Sum += integer;
            }
            return Sum;
        }else {
            int Sum = 0;
            for (Integer integer : Negative) {
                    Sum += - integer;
                    k --;
            }
            if (k % 2 == 0) {
                for (Integer integer : NonNegative) {
                    Sum += integer;
                }
            }else {
                for (Integer integer : NonNegative) {
                    Sum += integer;
                }
                if (Negative.isEmpty()) {
                    Sum -= 2 * NonNegative.get(0);
                }
                else if(NonNegative.isEmpty()){
                    Sum -= 2 * -Negative.get(Negative.size() - 1);
                }
                else {
                    Sum -= 2 * Math.min(NonNegative.get(0), -Negative.get(Negative.size() - 1));
                }
            }
            return Sum;
        }
```

### **134**. 加油站

在一条环路上有 `n` 个加油站，其中第 `i` 个加油站有汽油 `gas[i]` 升。

你有一辆油箱容量无限的的汽车，从第 `i` 个加油站开往第 `i+1` 个加油站需要消耗汽油 `cost[i]` 升。你从其中的一个加油站出发，开始时油箱为空。

给定两个整数数组 `gas` 和 `cost` ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 `-1` 。如果存在解，则 **保证** 它是 **唯一** 的。

> 示例 1:
>
> 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
> 输出: 3
> 解释:
> 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
> 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
> 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
> 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
> 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
> 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
> 因此，3 可为起始索引。
>
> 
>
> 示例 2:
>
> 输入: gas = [2,3,4], cost = [3,4,3]
> 输出: -1
> 解释:
> 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
> 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
> 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
> 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
> 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
> 因此，无论怎样，你都不可能绕环路行驶一周。

暴力法：穷举每个位置，然后对于每个起始位置，模拟其行驶情况。

<img src="https://i.imgur.com/iWD2CDV.png" alt="image-20230930161958861" style="zoom:80%;" />

```java
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int Gas = 0;
        for (int i = 0; i < gas.length; i++) {
            int j = (i + 1)  % gas.length;
            Gas = gas[i] - cost[i];
            while (Gas > 0 && j != i) {
                Gas += gas[j];
                Gas -= cost[j];
                j = (j + 1) % gas.length;
            }
            if (j == i && Gas >= 0) {
                return i;
            }
        }
        return -1;
    }
```

贪心很巧妙，妈的，但是我不想看了。说不出来哪里对，但是又说不出来哪里错。

### 135. 分发糖果

`n` 个孩子站成一排。给你一个整数数组 `ratings` 表示每个孩子的评分。

你需要按照以下要求，给这些孩子分发糖果：

- 每个孩子至少分配到 `1` 个糖果。
- 相邻两个孩子评分更高的孩子会获得更多的糖果。

请你给每个孩子分发糖果，计算并返回需要准备的 **最少糖果数目** 。



看代码随想录去。

```java
public int candy(int[] ratings) {
    int[] candyNum = new int[ratings.length];
    candyNum[0] = 1;
    for (int i = 1; i < ratings.length; i++) {
        if (ratings[i] > ratings[i - 1]) {
            candyNum[i] = candyNum[i - 1] + 1;
        }else {
            candyNum[i] = 1;
        }
    }
    for (int i = candyNum.length - 2; i >= 0 ; i --) {
        if (ratings[i] > ratings[i + 1]) {
            candyNum[i] = Math.max(candyNum[i], candyNum[i + 1] + 1);
        }
    }
    int sum = 0;
    for (int i : candyNum) {
        sum += i;
    }
    return sum;
}
```

### 860. 柠檬水找零

在柠檬水摊上，每一杯柠檬水的售价为 `5` 美元。顾客排队购买你的产品，（按账单 `bills` 支付的顺序）一次购买一杯。

每位顾客只买一杯柠檬水，然后向你付 `5` 美元、`10` 美元或 `20` 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 `5` 美元。

注意，一开始你手头没有任何零钱。

给你一个整数数组 `bills` ，其中 `bills[i]` 是第 `i` 位顾客付的账。如果你能给每位顾客正确找零，返回 `true` ，否则返回 `false` 。



> **示例 1：**
>
> ```
> 输入：bills = [5,5,5,10,20]
> 输出：true
> 解释：
> 前 3 位顾客那里，我们按顺序收取 3 张 5 美元的钞票。
> 第 4 位顾客那里，我们收取一张 10 美元的钞票，并返还 5 美元。
> 第 5 位顾客那里，我们找还一张 10 美元的钞票和一张 5 美元的钞票。
> 由于所有客户都得到了正确的找零，所以我们输出 true。
> ```
>
> **示例 2：**
>
> ```
> 输入：bills = [5,5,10,10,20]
> 输出：false
> 解释：
> 前 2 位顾客那里，我们按顺序收取 2 张 5 美元的钞票。
> 对于接下来的 2 位顾客，我们收取一张 10 美元的钞票，然后返还 5 美元。
> 对于最后一位顾客，我们无法退回 15 美元，因为我们现在只有两张 10 美元的钞票。
> 由于不是每位顾客都得到了正确的找零，所以答案是 false。
> ```



照顺序模拟就行了。

给20找零的时候优先用10 + 5 块的，因为10块的只能用来给20找零，而5块的更加万能。

```java
public boolean lemonadeChange(int[] bills) {
    int Five = 0, Ten = 0, Twenty = 0;
    for (int i = 0; i < bills.length; i++) {
        if (bills[i] == 5) {
            Five ++;
        }
        else if (bills[i] == 10) {
            // 找一张5块,收一张10块
            Five --;
            Ten ++;
            if (Five < 0) {
                return false;
            }
        }
        else {
            Twenty ++;
            if (!((Five >= 3) || (Five >= 1 && Ten >= 1))) {
                // 找3张5块的,或者 5 + 10.其他情况没办法正确找零,返回false。
                return false;
            }else {
                if (Five >= 3 && Ten == 0) {
                    Five -= 3;
                }
                if (Five >= 1 && Ten >= 1) {
                    Five --;
                    Ten --;
                }
            }
        }
    }
    return true;
}
```

### **406**.根据身高重建队列

假设有打乱顺序的一群人站成一个队列，数组 `people` 表示队列中一些人的属性（不一定按顺序）。每个 `people[i] = [hi, ki]` 表示第 `i` 个人的身高为 `hi` ，前面 **正好** 有 `ki` 个身高大于或等于 `hi` 的人。

请你重新构造并返回输入数组 `people` 所表示的队列。返回的队列应该格式化为数组 `queue` ，其中 `queue[j] = [hj, kj]` 是队列中第 `j` 个人的属性（`queue[0]` 是排在队列前面的人）。

> **示例 1：**
>
> ```
> 输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
> 输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
> 解释：
> 编号为 0 的人身高为 5 ，没有身高更高或者相同的人排在他前面。
> 编号为 1 的人身高为 7 ，没有身高更高或者相同的人排在他前面。
> 编号为 2 的人身高为 5 ，有 2 个身高更高或者相同的人排在他前面，即编号为 0 和 1 的人。
> 编号为 3 的人身高为 6 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
> 编号为 4 的人身高为 4 ，有 4 个身高更高或者相同的人排在他前面，即编号为 0、1、2、3 的人。
> 编号为 5 的人身高为 7 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
> 因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新构造后的队列。
> ```



先按身高降序排好序，然后再按照k，插入对应的位置即可。

排序完的people： [[7,0], [7,1], [6,1], [5,0], [5,2]，[4,4]]

插入的过程：

- 插入[7,0]：[[7,0]]
- 插入[7,1]：[[7,0],[7,1]]
- 插入[6,1]：[[7,0],[6,1],[7,1]]
- 插入[5,0]：[[5,0],[7,0],[6,1],[7,1]]
- 插入[5,2]：[[5,0],[7,0],[5,2],[6,1],[7,1]]
- 插入[4,4]：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]

```java
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o2[0] - o1[0];
            }else {
                return o1[1] - o2[1];
            }
        });
        LinkedList<int[]> Queue = new LinkedList<>();
        for (int[] ints : people) {
            Queue.add(ints[1], ints);
        }
        return Queue.toArray(new int[people.length][2]);
    }
```

### **452**. 用最少数量的箭引爆气球

有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 `points` ，其中`points[i] = [xstart, xend]` 表示水平直径在 `xstart` 和 `xend`之间的气球。你不知道气球的确切 y 坐标。

一支弓箭可以沿着 x 轴从不同点 **完全垂直** 地射出。在坐标 `x` 处射出一支箭，若有一个气球的直径的开始和结束坐标为 `x``start`，`x``end`， 且满足  `xstart ≤ x ≤ x``end`，则该气球会被 **引爆** 。可以射出的弓箭的数量 **没有限制** 。 弓箭一旦被射出之后，可以无限地前进。

给你一个数组 `points` ，*返回引爆所有气球所必须射出的 **最小** 弓箭数* 。

```java
public int findMinArrowShots(int[][] points) {
    // 根据气球直径的开始坐标从小到大排序
    // 使用Integer内置比较方法，不会溢出
    Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));

    int count = 1;  // points 不为空至少需要一支箭
    for (int i = 1; i < points.length; i++) {
        if (points[i][0] > points[i - 1][1]) {  // 气球i和气球i-1不挨着，注意这里不是>=
            count++; // 需要一支箭
        } else {  // 气球i和气球i-1挨着
            points[i][1] = Math.min(points[i][1], points[i - 1][1]); // 更新重叠气球最小右边界
        }
    }
    return count;
}
```

### 435. 无重叠区间

给定一个区间的集合 `intervals` ，其中 `intervals[i] = [starti, endi]` 。返回 *需要移除区间的最小数量，使剩余区间互不重叠* 。

使用总区间个数，减去非重叠区间的最大个数，就是需要移除的区间的最小个数。

和引爆气球那一题非常像。不过这里是按照区间的结尾进行的排序。

```java
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(o -> o[1]));
    int count = 0;
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= intervals[i - 1][1]) {
            count ++;
        }else {
            intervals[i][1] = Math.min(intervals[i][1], intervals[i - 1][1]);
        }
    }
    return intervals.length - count;
}
```

### **763**. 划分字母区间

给你一个字符串 `s` 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。

注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 `s` 。

返回一个表示每个字符串片段的长度的列表。



和前几道区间重叠的思路是类似的：先统计每一个字母第一次出现的位置，和最后一次出现的位置，记作一个长度为2的一维数组`[Start, End]`。

然后把统计的结果用一个二维数组记录下来，也就是代码里的`ints`。

然后按照`Start`对二维数组排序。然后统计相互重叠的区间，找到由重叠区间们构成的那个最长区间的起始位置和结束位置，那就是一个划分。

举个例子，以输入：`"ababcbacadefegdehijhklij"`为例。

我们得到的二维数组，排好序之后是：

<img src="https://i.imgur.com/0Uon8i4.png" alt="image-20231001164225714" style="zoom:67%;" />

一开始，重叠区间的起始位置是S = 0，结束位置E是8，然后我们不断遍历每个 一维数组构成的项，去更新结束位置E。

1. `[1,5]`的开始位置1小于E，所以和区间`[S,E]`重叠，更新E，E不变
2. `[4,7]`开始位置小于E，结束位置也小于E，也和`[S,E]`重叠，E不变。
3. `[9, 14]`的开始位置是9，它大于了`[S, E]`的结束位置E，说明它不与`[S,E]`重叠。因此此时`[S, E]`构成一个划分区间。`[9, 14]`则是新的划分区间的基础，我们把得到的划分区间的长度加入`ans`，然后把S和E分别更新为9和14。进入下一个重叠区间的寻找。

```java
public List<Integer> partitionLabels(String s) {
    char[] chars = s.toCharArray();
    HashMap<Character, int[]> characterHashMap = new HashMap<>();
    for (int i = 0; i < chars.length; i++) {
        if (!characterHashMap.containsKey(chars[i])) {
            characterHashMap.put(chars[i], new int[] {i, i});
        }else {
            int[] ints = characterHashMap.get(chars[i]);
            ints[1] = i;
        }
    }
    int[][] ints = new int[characterHashMap.size()][2];
    int i = 0;
    for (Character character : characterHashMap.keySet()) {
        int[] ints1 = characterHashMap.get(character);
        ints[i][0] = ints1[0];
        ints[i][1] = ints1[1];
        i ++;
    }
    Arrays.sort(ints, (o1, o2) -> {
        return o1[0] - o2[0];
    });
    ArrayList<Integer> ans = new ArrayList<>();
    int  S = ints[0][0], E = ints[0][1];
    for (int i1 = 1; i1 < ints.length; i1++) {
        if (E < ints[i1][0]) {
            // 由ints[i1]1构成的区间的起始位置大于E,说明它不与区间 [S,E] 重叠
            ans.add(E - S + 1);
            S = ints[i1][0];
            E = ints[i1][1];
        }else {
            // 否则区间 ints[i1] 的起始位置小于E,与区间 [S,E] 重叠. 因此看看要不要更新E.
            E = Math.max(E, ints[i1][1]);  // 如果E的值和原来一样就说明 ints[i1]是 [S, E]的一个子区间.否则只是部分重合.
        }
    }
    ans.add(E - S + 1);
    return ans;
}
```

### 56. 合并区间

给出一个区间的集合，请合并所有重叠的区间。

> 示例 1:
>
> - 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
> - 输出: [[1,6],[8,10],[15,18]]
> - 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].

其实前一道题就是在合并区间。把前面的代码改改就能用了。把前一道题排序之后的代码照抄下来，改改就行了。

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, ((o1, o2) -> {
        return o1[0] - o2[0];
    }));
    ArrayList<int[]> ans = new ArrayList<>();
    int S = intervals[0][0], E = intervals[0][1];
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] > E) {
            ans.add(new int[]{S, E});
            S = intervals[i][0];
            E = intervals[i][1];
        }else {
            E = Math.max(E, intervals[i][1]);
        }
    }
    ans.add(new int[]{S, E});
    return ans.toArray(new int[ans.size()][2]);
}
```

### 738. 单调递增的数字

当且仅当每个相邻位数上的数字 `x` 和 `y` 满足 `x <= y` 时，我们称这个整数是**单调递增**的。

给定一个整数 `n` ，返回 *小于或等于 `n` 的最大数字，且数字呈 **单调递增*** 。

> **示例 2:**
>
> ```
> 输入: n = 1234
> 输出: 1234
> ```
>
> **示例 3:**
>
> ```
> 输入: n = 332
> 输出: 299
> ```



```java
public int monotoneIncreasingDigits(int n) {
    char[] chars = String.valueOf(n).toCharArray();
   int Start = chars.length;
    for (int i = chars.length - 2; i >= 0; i--) {
        Integer integer = Integer.valueOf(String.valueOf(chars[i]));
        Integer integer1 = Integer.valueOf(String.valueOf(chars[i + 1]));
        if (integer > integer1) {
            chars[i] = Character.forDigit(integer - 1, 10);
            chars[i + 1] = '9';
            Start = i + 1;
        }
    }
    for (int i = Start; i < chars.length; i++) {
        chars[i] = '9';
    }
    return Integer.parseInt(String.valueOf(chars));
}
```



## 动态规划

<!--0916，越过了贪心的大部分内容，先开启了动态规划-->

### 509. 斐波那契数

### 70. 爬楼梯

### 746. 使用最小花费爬楼梯

和70是比较类似的，要到第i个位置有两种办法：

1. 从第i - 2个位置向前跳两步。总开销是：从起点到达i-2处的开销，再加上从i-2处起跳所花费的开销
2. 从第i - 1个位置向前跳一步。总开销是：从起点到达i-1处的开销，再加上从i-1处起跳所花费的开销

取二者中的较小值，作为到达第i个位置的总开销即可。

```java
    public int minCostClimbingStairs(int[] cost) {
        int[] overhead = new int[cost.length + 1];
        overhead[0] = 0;
        overhead[1] = 0;
        for (int i = 2; i < overhead.length; i++) {
            overhead[i] = Math.min(overhead[i - 1] + cost[i - 1], overhead[i - 2] + cost[i - 2]);
        }
        return overhead[cost.length];
    }
```

<!--往下是20230917 -->

### 62.不同路径

这里只写一维数组版本。

一维数组的版本，每次计算下一行的时候，数组存储的是上一行。然后逐个计算，逐个替换掉上一行的每个元素。

具体而言，对于当前计算到的这个格子，有两条路可以到达它，一是它左边，二是它上方。前者就是这个格子的前一个元素（它已经计算过了，是本行的），后者就是当前这个格子目前所存储的值（因为它还是上一行的值）。

所以`dp[i] += dp[i - 1]`

```java
    for (int i = 0; i < n; i++) dp[i] = 1;
    for (int j = 1; j < m; j++) {
        for (int i = 1; i < n; i++) {
            dp[i] += dp[i - 1];
        }
    }
    return dp[n - 1];
```

### 63. 不同路径 II

一个机器人位于一个 `m x n` 网格的左上角 （起始点在下图中标记为 “Start” ）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。

现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？

网格中的障碍物和空位置分别用 `1` 和 `0` 来表示。

 

```java
public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length]; // dp[i][j]:从起点到(i,j)的路径数
    dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
    for (int i = 1; i < dp[0].length; i++) {
        /*
        如果(0,i)位置本身就有障碍的话，那dp[0][i] = 0。
        否则才等于obstacleGrid[0][i - 1] == 1 ? 0 : dp[0][i - 1]，也即如果前一个格子有障碍也是0，否则和前一个格子的dp相等
        填充dp数组的其他部分也同理。
         */
        dp[0][i] = obstacleGrid[0][i] == 1 ? 0 : (obstacleGrid[0][i - 1] == 1 ? 0 : dp[0][i - 1]);
    }
    for (int i = 1; i < dp.length; i++) {
        dp[i][0] = obstacleGrid[i][0] == 1 ? 0 : (obstacleGrid[i - 1][0] == 1 ? 0 : dp[i - 1][0]);
    }
    for (int i = 1; i < dp.length; i++) {
        for (int j = 1; j < dp[0].length; j++) {
            if (obstacleGrid[i][j] == 1) { // 如果本身就有障碍，那肯定不可达
                dp[i][j] = 0;
            }
            else {
                // 否则看看自己的上方和左方。上方有障碍的话上方路径是0，否则是上方的dp。左方同理
                dp[i][j] = (obstacleGrid[i - 1][j] == 1 ? 0 : dp[i - 1][j]) + (obstacleGrid[i][j - 1] == 1 ? 0 : dp[i][j - 1]);
            }
        }
    }
    return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
}
```

### 343. 整数拆分

给定一个正整数 `n` ，将其拆分为 `k` 个 **正整数** 的和（ `k >= 2` ），并使这些整数的乘积最大化。

首先还是要明确`dp[i]`的定义：将正整数`i`拆分成`k`个正整数时，这些整数的最大乘积。

对于一个数`i >= 2`，我们有两种拆分的方法：

1. 将其拆分成两个正整数的和，`i = j + (i- j)`。此时乘积为 `j * (i- j)`。
2. 将其拆分成多个正整数的和，也就是保留`j`，并且将正整数`i - j`进一步拆分，而正整数`i- j`进一步拆分，它的最大乘积就是`dp[i - j]`。此时乘积为 `j * dp[i - j]`
3. 而`j`则可以从`1`取到`i - 1`，所以这需要一个循环来遍历每一种情况。

我们取二者中的较大者作为`dp[i]`

因为0不是正整数，无法拆分，所以`dp[0] = 0`。因为1无法拆分成两个正整数的和，所以乘积为0`dp[1] = 0`。

```java
public int integerBreak(int n) {
    int[] dp = new int[n + 1]; // 因为要保存n = 1,0的dp。n >= 2，从0到n总共n + 1个数
    dp[0] = 0; // 0
    dp[1] = 0; // 1
    for (int i = 2; i < dp.length; i++) {
        for (int j = 1; j < i ; j++) {
            int tmp = i - j;
             // 这里不要写成dp[i] = Math.max(tmp * j, dp[tmp] * j)。还要跟dp当前的值去做比较。
            dp[i] = Math.max(dp[i],Math.max(tmp * j, dp[tmp] * j));
        }
    }
    return dp[n];
}
```

### 96.不同的二叉搜索树

给你一个整数 `n` ，求恰由 `n` 个节点组成且节点值从 `1` 到 `n` 互不相同的 **二叉搜索树** 有多少种？返回满足题意的二叉搜索树的种数。

这个题的dp数组的语义应该是很明显的，`dp[i]`代表由`i`个节点组成且节点值为`1`到`i`的不同的BST的种数。

但是递推公式是什么样的？

在回答这个问题之前，我首先要提出另外一个问题。由三个节点1,2,3构成的BST种类数，和由3个节点4,5,6构成的BST种类数，是否是一样的？

答案毫无疑问，种类数是相等的。我们只需要用4,5,6分别去取代1,2,3在BST中的位置，即可得到4,5,6所构成的全部的BST。

我们将这个结论推广一下，只要是n个连续的整数，他们构成的不同的BST数目，都是一样的。

也就是说，`dp[i]`的定义，其实更应该是，由`i`个连续的整数所构成的不同的BST的数目。

也就是说，`dp[3]`不止可以代表由`1,2,3`这3个节点构成的不同的BST的数目，它可以代表任意三个连续整数构成的BST的数目。dp数组的意义是多重的。



那么接下来，假如我们要求`dp[i]`，但是我们还是按照`dp[i]`最开始的定义，因为这是按题目要求的定义。

那么我们要怎么做呢？

我们按照根节点来分类：

1. 以1作为根节点的（由1到i总共i个节点构成的）BST有多少棵
2. 以2作为根节点的BST有多少棵
3. 以3作为根节点的BST有多少棵
4. 以此类推，直到以`i`为根节点的BST有多少棵。

我们把这些求和，即可得到`dp[i]`

那么以3为根节点的BST有多少棵，又怎么求呢？

因为3是根节点，所以1和2两个节点构成左子树，4,5,6一直到i构成右子树。

左子树的种类乘以右子树的种类，就是以3为根节点的不同的BST的种类数。

而左子树有多少种？

按照我们dp数组的定义，毫无疑问就是`dp[2]`。

但是右子树呢？这个时候我们就要使用dp数组的第二重意义了。

我们要把右子树的种类问题，换一个问法来问：以`4,5,6,`一直到`i`，**这`i - 3`个连续整数，构成的BST有多少种**？

答案显然了，是`dp[i  - 3]`。

所以我们求出了：以3作为根节点的，（**由1到i总共i个节点构成的**）BST有多少棵。

**当然，还有一点特殊情况，那就是假如左子树0个节点，右子树n - 2个节点，这种情况下，左子树的种类是1，不是0**。

依葫芦画瓢即可求出其他几个类了。



毫无疑问，`dp[0] = 1`（不是0，空树也是一种树），`dp[1] = 1`。

```java
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        if (n <= 2) {
            return n;
        }
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <=n ; i++) { // i是dp[i]的i，也即我们要求以1到i这i个数构成的BST数目。
            
            // 内层循环穷举根节点依次为1,2,3,直到i的情况,从而求出dp[i]
            for (int root = 1; root <= i ; root++) {
                // 左子树的节点: 1到root - 1。右子树的节点: root + 1到i
                int LeftNum = root - 1, RightNum = i - root;
                dp[i] += dp[LeftNum] * dp[RightNum];
            }
        }
        return dp[n];
    }
```

### 0-1背包问题

01背包：[416. 分割等和子集](https://leetcode-cn.com/problems/partition-equal-subset-sum/) [474. 一和零](https://leetcode-cn.com/problems/ones-and-zeroes/) [494. 目标和](https://leetcode-cn.com/problems/target-sum/) [879. 盈利计划](https://leetcode-cn.com/problems/profitable-schemes/) [1049. 最后一块石头的重量 II](https://leetcode-cn.com/problems/last-stone-weight-ii/) [1230. 抛掷硬币](https://leetcode-cn.com/problems/toss-strange-coins/)

完全背包：[1449. 数位成本和为目标值的最大数字](https://leetcode-cn.com/problems/form-largest-integer-with-digits-that-add-up-to-target/) [322. 零钱兑换](https://leetcode-cn.com/problems/coin-change/) [518. 零钱兑换 II](https://leetcode-cn.com/problems/coin-change-2/) [279. 完全平方数](https://leetcode-cn.com/problems/perfect-squares/)

0-1背包：每种物品只有一个

完全背包：每种物品都有无数个

多重背包：每种物品的数量各不相同

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20210117171307407.png" alt="416.分割等和子集1"  />

#### 纯0-1背包

有n件物品和一个最多能背重量为w 的背包。第i件物品的重量是weight[i]，得到的价值是value[i] 。**每件物品只能用一次**（每件物品只有一个），求解将哪些物品装入背包里物品价值总和最大。



暴力的方法是回溯，每个物品只有两种选择：拿或者不拿。使用回溯法搜索出所有的情况。但是复杂度显然是$O(2^n)$的。



仍然还是，首先要明确dp数组的含义：`dp[i][j]`表示，在背包容量为`j`的时候，从`0`号到`i`物品中选择，能够获取的最大的价值。

对于`dp[i][j]`，我们需要考虑的是第`i`个物品是否拿取。这有两种情况：

1. 我们拿取物品`i`，这也意味着我们要预先在背包中腾出容纳物品`i`的空间。拿取物品`i`，就相当于，我们在背包容量为`j-weight[i]`的情况下，先从物品`0`到物品`i-1`中拿取，获得最大的价值，然后再将物品`i`放入我们预先腾空出来的背包空间内，`j-weight[i]`就是预先腾出了`weight[i]`的空间用于放入第`i`个物品。
2. 我们不拿物品`i`。那么物品`i`就相当于不存在，我们考虑的就仍然还是从`0`到第`i-1`号物品中，在背包容量为`j`的情况下，拿取的最大值。

然后我们在两种选择之中挑选较大的那个作为更优解。

当然，以上考虑的都是容量`j`放得下物品i的情况，如果放不下物品j的话，那毫无疑问，跟不拿物品`j`的情况2是类似的。



dp二维数组的初始化：

首先背包容量为0的时候，肯定毫无疑问什么东西都放不了，价值都是0。

其次考虑只有第0个物品的时候，这个时候因为我们就只有一个物品可以选，无需考虑就可以得知在背包容量为`j`的时候的最大价值：放得下就放进去，放不下，那就是0。

有了这两个基础设施之后，我们的dp数组就可以通过状态转移公式推断出全部的答案。



关于遍历顺序的问题：是先遍历物品，还是先遍历背包容量。实际上两种都可以，都能够做到值的填充是先填充左上部分的。具体的看代码随想录的讲解。

```java
/*
* 基本的0-1背包问题
* 每个物品只有一个
* 对于一个物品，要么选它，要么不选它。不能选零点几个的它，要么是0个，要么是1个
* */
public int ZeroOneBackPack(int BackpackCapacity, int[] Weight, int[] Value) {
    int ItemCount = Weight.length;
    /*
    dp这里列数要+1是因为我们需要考虑背包容量为0的起始情况,
    0到BackpackCapacity总共是BackpackCapacity + 1个数
    */
    int[][] dp = new int[ItemCount][BackpackCapacity + 1];
    for (int i = 0; i < ItemCount; i++) {
        dp[i][0] = 0;
    }
    for (int i = 0; i < dp[0].length; i++) {
        // 只有物品0,在背包容量为i的情况下,放得下物品0.最大价值就是value[0],否则是0
        dp[0][i] = i >= Weight[0] ? Value[0] : 0;
    }
    for (int i = 1; i < ItemCount; i++) {
        for (int j = 1; j <= BackpackCapacity; j++) {
            if (j < Weight[i]) {
                // 背包容量j放不下物品i，那么就相当于在物品 0 到物品 i-1 里，背包容量为j的情况下进行选择。
                dp[i][j] = dp[i - 1][j];;
            }
            else {
                // 在背包容量为j的情况下,如果选择物品i
                int ChooseI = dp[i - 1][j - Weight[i]] + Value[i];
                // 在背包容量为j的情况下,不选择物品i
                int DoNotChooseI = dp[i - 1][j];
                dp[i][j] = Math.max(ChooseI, DoNotChooseI);
            }
        }
    }
    return dp[ItemCount - 1][BackpackCapacity];
}
```

#### 0-1背包，但是一维数组（没看完）

我们观查一下上面的求dp[i]的代码：

```java
if (j < Weight[i]) {
    // 背包容量j放不下物品i，那么就相当于在物品 0 到物品 i-1 里，背包容量为j的情况下进行选择。
    dp[i][j] = dp[i - 1][j];;
}
else {
    // 在背包容量为j的情况下,如果选择物品i
    int ChooseI = dp[i - 1][j - Weight[i]] + Value[i];
    // 在背包容量为j的情况下,不选择物品i
    int DoNotChooseI = dp[i - 1][j];
    dp[i][j] = Math.max(ChooseI, DoNotChooseI);
}
```

可以发现，每次求`dp[i]`这一行 时候，我们唯一会用到的，就只有上一行`dp[i - 1]`。所以我们为什么不直接使用一个一维数组，来保存“上一行”，然后计算本行的时候边计算边覆盖这一个一维数组，把本行变成“上一行”，这样下下行的计算就也可以从这个一维数组里面读取它的上一行。

但是，**为了防止上一层循环的`dp[0,...,j-1]`被覆盖**，循环的时候 j 只能从大到小，**逆向枚举**。

于是这样递推公式就变成了：

```java
dp[j] = max(dp[j], dp[j - weight[i]] + value[i])
```

#### 416. 分割等和子集

给你一个 **只包含正整数** 的 **非空** 数组 `nums` 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。



我们记数组总和为Sum，这道题相当于一个01背包问题：

1. 每个物品（数字）的价值和重量都是数字本身
2. 背包的容量是Sum / 2

这样，假如背包能够恰好装满，(则价值刚好等于容量，是Sum / 2），就说明我们可以从中选取某些数字使得背包装满，且这些数字的和恰好为Sum / 2。

因此最后只需要看看背包的最大价值是否恰好等于容量即可。



当然，如果Sum是一个奇数的话，那么必然不可能，直接返回`false`

```java
public boolean canPartition(int[] nums) {
    int Sum = 0;
    for (int num : nums) {
        Sum += num;
    }
    if (Sum % 2 != 0) {
        return false;
    }
    int i = BackPacking(Sum / 2, nums, nums);
    return (Sum / 2) == i;
}
private int BackPacking(int capacity, int[] weight, int[] value) {
    int[][] dp = new int[weight.length][(capacity + 1)];
    for (int i = 0; i < dp.length; i++) {
        dp[i][0] = 0;
    }
    for (int i = 0; i < dp[0].length; i++) {
        dp[0][i] = i >= weight[0] ? value[0] : 0;
    }
    for (int Item = 1; Item < dp.length; Item++) {
        for (int capa = 1; capa <= capacity ; capa++) {
            if (capa < weight[Item]) {
                dp[Item][capa] = dp[Item - 1][capa];
            }else {
                 // 这里永远不要忘记给dp[Item - 1][capa - weight[Item]]再加上一个value[Item]。不要忘记把物品Item本身的价值加上
                dp[Item][capa] = Math.max(dp[Item - 1][capa], dp[Item - 1][capa - weight[Item]] + value[Item]);
            }
        }
    }
    return dp[weight.length - 1][capacity];
}
```

#### 1049.最后一块石头的重量II

有一堆石头，每块石头的重量都是正整数。

每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：

如果 x == y，那么两块石头都会被完全粉碎；

如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。

最后，最多只会剩下一块石头。返回此石头最小的可能重量。如果没有石头剩下，就返回 0。

> 示例：
>
> - 输入：[2,7,4,1,8,1]
> - 输出：1
>
> 解释：
>
> - 组合 2 和 4，得到 2，所以数组转化为 [2,7,1,8,1]，
> - 组合 7 和 8，得到 1，所以数组转化为 [2,1,1,1]，
> - 组合 2 和 1，得到 1，所以数组转化为 [1,1,1]，
> - 组合 1 和 1，得到 0，所以数组转化为 [1]，这就是最优值。

本题其实就是尽量让石头分成重量相同的两堆，相撞之后剩下的石头最小，**这样就化解成01背包问题了**。和416分割等和子集非常的像。



当然这道题还是有一点轻微的不同。

因为和Sum / 2是向下取整的，所以背包中的石头是必然少于剩余的石头的。假如我们记背包里的那一堆石头的最大价值为`backpack`，那么另外一堆就是`Sum - backpack`。用后一堆去减前一堆即可。

```java
public int lastStoneWeightII(int[] stones) {
    int Sum = 0;
    for (int stone : stones) {
        Sum += stone;
    }
    int backpack = Backpack(Sum / 2, stones, stones);
    return Sum  - 2 * backpack;
}
private int Backpack(int capacity, int[] weight, int[] value) {
    int[][] dp = new int[weight.length][capacity + 1];
    for (int i = 0; i < dp[0].length; i++) {
        dp[0][i] = i >= weight[0] ? value[0] : 0;
    }
    for (int i = 1; i < dp.length; i++) {
        for (int j = 1; j < capacity + 1; j++) {
            if (j < weight[i]) {
                dp[i][j] = dp[i - 1][j];
            }else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
            }
        }
    }
    return dp[weight.length - 1][capacity];
}
```

#### **494**.目标和（代码逻辑有问题）

给你一个非负整数数组 `nums` 和一个整数 `target` 。

向数组中的每个整数前添加 `'+'` 或 `'-'` ，然后串联起所有整数，可以构造一个 **表达式** ：

- 例如，`nums = [2, 1]` ，可以在 `2` 之前添加 `'+'` ，在 `1` 之前添加 `'-'` ，然后串联起来得到表达式 `"+2-1"` 。

返回可以通过上述方法构造的、运算结果等于 `target` 的不同 **表达式** 的数目。



其实我的第一反应是回溯穷举，但是这就跟背包问题一样，每个整数有`+`与`-`两个状态，如果要穷举的话，毫无疑问是指数的复杂度。



当然，回溯的话可以转化成组合总和问题：我们假定给定数组里的数是n0,n1,n2,n3...。

然后我们可以按照符号分为两组：全部是加的一组和全部是减号的一组，比如，就会变成：(n0+n3+n5+n7+n9) - (n2 + n4 + n6 +n8)  = target。

而给定数组的总和是固定的，因此我们可以把其中一组用另外一组表示：

- 记(n2 + n4 + n6 +n8) = R，总和为Sum。那么就有：(Sum - R) - R = target。也即R = (Sum + target) / 2
- 因此，问题可以转变为，从给定数组里面挑选一些数，使得他们的和为`(Sum + target) / 2`

然后拿Leetcode的组合总和的代码稍微改改，就是回溯解决的方式了，不过会超时。



如果使用0-1背包呢？其实和上一道题很像，假如：(n0+n3+n5+n7+n9) - (n2 + n4 + n6 +n8)  = target

那么：这是第一堆石头：(n0+n3+n5+n7+n9)，然后这是第二堆：(n2 + n4 + n6 +n8)。然后我们要的是让这两堆石头的差刚好是Target。

我们还是记总和为Sum，加法部分的和为X，那么减法部分的和就是Y。就有X - Y = target = (Sum - Y) - Y 

即有Y = (Sum + target) / 2。

因此问题变成，要把容量为Y的包装满，有多少种方法？

首先我们要知道的是，假如`Sum +target`不是偶数的话，问题是无解的，因为Y是整数（是减法部分的和），所以假如`Sum + target`是奇数的话，Y就带有小数了，不是整数。

因此：

```java
if ((target + sum) % 2 == 1) return 0; // 此时没有方案
```

同样，如果`target`的绝对值大于Sum的话，也是无解的。因为Sum是数组里的全部数的和（数组里的每个数都是非负数，因此全部取加号肯定是和最大的），Sum就是这个数组的各种组合的上限，-Sum则是对应的下限。

```java
if (Matg.abs(target)> sum) return 0; // 此时没有方案
```



然后我们明确`dp[i][j]`的含义：从`nums[0:i]`里选取数，把容量为`j`的背包填满，有`dp[i][j]`种方法。

递推公式？

1. 当容量为0时，填满的方法只有唯一一种——什么都不选。
2. 当只有第0个数可以选时，只有`j == num[0]`时方法数为1，其他情况都为0。
3. 考虑`dp[i][j]`：
   1. 如果`j < nums[i]`，选不了`nums[i]`，那么 `dp[i][j] = dp[i - 1][j]`
   2. 如果 `j >= nums[i]`，那么`nums[i]`可选可不选，两种选择加起来，那么 `dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i]]`

代码写出来有bug，不知道哪里的逻辑有问题。

#### **474**. 一和零（二维容量的0-1背包）

给你一个二进制字符串数组 `strs` 和两个整数 `m` 和 `n` 。

请你找出并返回 `strs` 的最大子集的长度，该子集中 **最多** 有 `m` 个 `0` 和 `n` 个 `1` 。

这里的“最大”指的是子集中元素的个数最多，返回的长度也就是子集的元素个数。

> 示例 1：
>
> 输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
> 输出：4
> 解释：最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
> 其他满足题意但较小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"} 不满足题意，因为它含 4 个 1 ，大于 n 的值 3 。



这个问题是一个**多维**的0-1背包问题。之所以说是多维的，是因为我们的纯0-1背包问题，背包的容量只有一个维度，具体表现在dp数组上，就是一个维度。

而这个问题，我们可以看做这个背包是一个比较特殊的背包，它的容量有两个维度：一个维度是可以放置的0的个数，另外一个维度是可以放置的1的个数。`strs`中的每个字符串就是一个物品，物品的重量也有两个维度：0的“重量”和1的“重量”。而物品的价值我们规定为1，这样，当背包里的价值最大的时候，也就是物品的个数最大的时候。

以往我们的纯背包问题，dp数组只有两个维度，一个维度代表容量`j`，另外一个维度代表从前`i`个物品选择。

但是这里，我们的背包的容量就有两个分量，所以我们需要使用两个维度来分别代表两个容量分量，并且保留代表物品的那个维度。

因此我们需要一个三维的dp数组。

`dp[i][j][k]`的意义是：考虑前i个字符串，背包可以容纳j个1和k个0的时候，所能取得的最大价值。

递推公式我们类比一维容量的背包，可知：

1. 对于第i个物品，如果背包容量不够，那么最大价值就是`dp[i - 1][j][k]`
2. 如果背包的两个容量分量都够，那么最大价值就是 `Math.max(dp[i - 1][j][k], dp[i - 1][j - OneNum][k - ZeroNum] + 1)`
   - 其实还是，要么不选物品`i`。要么选，但是要预先在两个维度上预留出给物品`i`的空间，并且加上物品`i`自己的价值。

dp数组的初始化也是类似的：任意一个容量分量为0的时候，最大价值都是0。只有第0个物品可选的时候，最大价值要么是0（放不进去），要么是1（放进去了）。

```java
public int findMaxForm(String[] strs, int m, int n) {
    int[] ZeroWeight = new int[strs.length];
    int[] OneWeight = new int[strs.length];
    int i = 0;
    for (String str : strs) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (aChar == '0') {
                ZeroWeight[i] ++;
            }else {
                OneWeight[i] ++;
            }
        }
        i ++;
    }
    int i1 = BackPacking_Squared(m, n, ZeroWeight, OneWeight, 1);
    return i1;
}

private int BackPacking_Squared(int ZeroCapa, int OneCapa, int[] ZeroWeight, int[] OneWeight, int Value) {
    int[][][] dp = new int[ZeroWeight.length][ZeroCapa + 1][OneCapa + 1];
    for (int i = 0; i < dp[0].length; i++) {
        for (int j = 0; j < ZeroCapa + 1; j++) {
            for (int k = 0; k < OneCapa + 1; k++) {
                if (j >= ZeroWeight[0] && k >= OneWeight[0]) {
                    dp[0][j][k] = Value;
                }else {
                    dp[0][j][k] = 0;
                }
            }
        }
    }
    for (int i = 1; i < ZeroWeight.length; i++) {
        for (int j = 0; j < ZeroCapa + 1; j++) {
            for (int k = 0; k < OneCapa + 1; k++) {
                if (j >= ZeroWeight[i] && k >= OneWeight[i]) {
                    dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - ZeroWeight[i]][k - OneWeight[i]] + Value);
                }else {
                    dp[i][j][k] = dp[i - 1][j][k];
                }
            }
        }
    }
    return dp[ZeroWeight.length - 1][ZeroCapa][OneCapa];
}
```

### 完全背包问题

完全背包就是，背包容量为W，总共有N件物品，每种物品的重量为`Weight[i]`，价值为`Value[i]`。**每种物品都有无限多个（因此同一种物品可以选多个放入背包）**。

**完全背包和01背包问题唯一不同的地方就是，每种物品有无限件**。

#### 纯完全背包

同样，思维方式和0-1背包类似：

- 首先要明确dp数组的含义：`dp[i][j]`表示，在背包容量为`j`的时候，从`0`号到`i`物品中选择，能够获取的最大的价值。
- 对于`dp[i][j]`:有两种情况，**并且这个递推公式对于第0个物品也是成立的，因为第0个物品也可以放入多个**。
  1. 容量太小放不下，不装入第i种物品，即`dp[i−1][j]`，同01背包；
  2. 容量可以装入（至少一个）第i种物品，此时和01背包不太一样，因为每种物品有无限个（但注意书包限重是有限的），所以此时不应该转移到`dp[i−1][j−w[i]]`而应该转移到`dp[i][j−w[i]]`，即装入第i种商品后还可以再继续装入第种商品。

所以我们更加推荐的二维数组的初始化方式是，既考虑，背包容量为0的时候，也考虑只有0种物品可以选择的时候。

也即，`dp[0][j]`表示的是，当只有前0种物品可以选择的时候，背包容量为j的时候，所能得到的最大值（毫无疑问是0，因为没有东西可以选）。这样对于第1个物品，也可以直接用状态转移方程来计算，而不必初始化。

所以状态转移公式为：

1. 如果放不下第i种物品，那就是`dp[i−1][j]`
2. 如果放得下，那还是同样需要考虑，是放，还是不放，在两种选择里选价值更高的那个。

```java
dp[i][j] = max(dp[i−1][j], dp[i][j−weight[i]]+value[i]) // j >= w[i]
```

和01背包问题类似，也可进行空间优化，优化后不同点在于这里的 j 只能**正向枚举**而0-1背包只能逆向枚举，因为这里的max第二项是`dp[i]`而01背包是`dp[i-1]`，即这里就是需要覆盖而01背包需要避免覆盖。

人话就是，0-1背包计算的时候用的是一维数组中存储的上一层的值，所以要从右往左计算新的值，这样计算新值的时候，从右往左，不会覆盖被用到的旧值。

而完全背包，计算的时候也是使用左边的值，但是那个左边的值，是本层左边的值，本身就是要覆盖掉上一层的值的。



当然，完全背包应该还有另外一种思路，那就是从装入第 i 种物品多少件出发，01背包只有两种情况即取0件和取1件，而这里是取0件、1件、2件...直到超过限重（k > j/w[i]），所以状态转移方程为：

```java
dp[i][j] = Math.max(dp[i - 1][j - k * weight[i]] + k * value[i]), 0 <= k * weight[i] <= j;
```

这个式子其实很好理解，当k取0的时候，就是不放第`i`件物品，`dp[i][j] = dp[i−1][j]` ，`当k> 0` 的时候，就代表我们放入了k件第`i`种物品，因此要在从前`i - 1`件选择的时候就预先留出能够放`k`件第`i`种物品的空间。



我个人还是喜欢前一种，因为状态转移公式比较简单。

```java
public int CompleteBackpack(int capacity, int[] weight, int[] value) {
    int[][] dp = new int[weight.length + 1][capacity + 1];

    // 如果是只有0件物品可以选，那么最大价值肯定是0
    for (int i = 0; i < dp[0].length; i++) {
        dp[0][i] = 0;
    }
    // 如果背包的容量为0，那么最大价值肯定也是0
    for (int i = 0; i < dp.length; i++) {
        dp[i][0] = 0;
    }

    // 接下来考虑在前i种物品里选择的时候，背包容量为j > 0的时候，最大价值是多少(物品从1开始编号)。

    for (int item = 1; item <= weight.length; item ++) {
        for (int capa = 0; capa <= capacity ; capa++) {
            if (capa < weight[item - 1]) { // 这里稍微注意一下就是, 第i个物品的重量和价值的索引是i - 1.
                // 容量太小放不下哪怕一件第item种物品,那就不放
                dp[item][capa] = dp[item - 1][capa];
            }else {
                // 从不放,和放多件里选收益更大的那个.因为同一件物品可以放多个，所以是dp[item][capa - weight[item]]
                dp[item][capa] = Math.max(dp[item - 1][capa], dp[item][capa - weight[item - 1]] + value[item - 1]);
            }
        }
    }
    return dp[weight.length][capacity];
}
```

#### 518.零钱兑换II

给你一个整数数组 `coins` 表示不同面额的硬币，另给一个整数 `amount` 表示总金额。

请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 `0` 。

假设每一种面额的硬币有无限个。 

> 示例 1：
>
> 输入：amount = 5, coins = [1, 2, 5]
> 输出：4
> 解释：有四种方式可以凑成总金额：
> 5=5
> 5=2+2+1
> 5=2+1+1+1
> 5=1+1+1+1+1



每种硬币都有无限个的话，一眼完全背包。背包容量就是`Amount`，`coins`是其价值，而每个硬币的重量是其价值。我们要的是把背包填满。

当然其实，我们根本不关心每个硬币的价值，因为我们要求的并不是最大价值，而是凑齐指定金额的组合数。

`dp[i][j]`表示，从第1个到第`i`个硬币中选择组合，能够凑满金额`j`的组合总数。

那么状态转移方程的话：

1. 如果金额为0，那么凑齐0元的方式只有一种，因此`dp[i][0] = 0`
2. 如果只能从第0个硬币选的话，因为无硬币可选，那么除了金额为0的有1种方案，其他任意金额都凑不齐，`dp[0][j] = 0`
3. 对于`dp[i][j]`：
   1. 假如第`i`种硬币的票面价值超过了总金额`j`，那就不选它，相当于还是在第`1`到第`i-1`种硬币里组合凑齐总金额`j`，`dp[i][j] = dp[i - 1][j]`。
   2. 假如第`i`种硬币的票面价值没有超过总金额`j`，那么可以选，也可以不选。~~在两种选择里取组合数更大的选择，`dp[i][j] = Max(dp[i - 1][j], dp[i][j - coins[i]])`~~ 。**这两种选择是各自独立的，因此要把组合数相加**，而不是选组合数更大的那一个。

```java
public int change(int amount, int[] coins) {
    int[][] dp = new int[coins.length + 1][amount + 1];

    // 如果总金额为0.那么方案只有1种
    for (int i = 0; i < dp.length; i++) {
        dp[i][0] = 1;
    }
    // 如果只能从前0种硬币里选择,那么除了金额为0的有1种方案, 方案只有0种。
    for (int i = 1; i < dp[0].length; i++) {
        dp[0][i] = 0;
    }

    for (int i = 1; i <= coins.length; i++) {
        for (int j = 1; j <= amount ; j++) {
            if (j < coins[i - 1]) {
                dp[i][j] = dp[i - 1][j];
            }else {
                dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
            }
        }
    }
    return dp[coins.length][amount];
}
```

#### **377**. 组合总和 Ⅳ

给你一个由 **不同** 整数组成的数组 `nums` ，和一个目标整数 `target` 。请你从 `nums` 中找出并返回总和为 `target` 的元素组合的个数。

> 示例 1：
>
> 输入：nums = [1,2,3], target = 4
> 输出：7
> 解释：
> 所有可能的组合为：
> (1, 1, 1, 1)
> (1, 1, 2)
> (1, 2, 1)
> (1, 3)
> (2, 1, 1)
> (2, 2)
> (3, 1)
> 请注意，顺序不同的序列被视作不同的组合。

其实我的第一反应是回溯 + 剪枝。

但是这个，看了示例之后，一眼完全背包问题，每个数就是一个物品，每个数都可以使用无穷多次。

其实和前一道518.零钱兑换II也很像吧。

`dp[i][j]`的意义是：在从第`1`到第`i`个数里选择，能够凑出目标整数`j`的组合个数。

~~那么状态转移公式：~~

1. ~~如果只能从第0个数里选，那么除了`target == 0`有一种组合以外，其他目标数的组合数都是0。~~
2. ~~如果目标数`target == 0`，那么除非`nums`里有0，否则组合数只能为0。~~
3. ~~考虑`dp[i][j]`:~~
   1. ~~如果`nums[i] > j`，那么不能选第`i`个数，就相当于只能从第`1`到第`i-1`个数里凑出目标和`j`， `dp[i][j] = dp[i - 1][j]`~~
   2. ~~否则对于第`i`个数来说，也有选和不选两种选项，我们把这两种选项的组合数相加。`dp[i][j] = dp[i - 1][j] + dp[i][j - nums[i - 1]]`~~

说实话，一直把问题往完全背包问题上套，转来转去把我脑子转晕了。



但是如果把这道题看做是一个爬楼梯问题的话，可以这么想：

1. 楼梯的阶数一共是target，**每次爬楼梯可选的层数从nums数组中挑选**，问总共有多少种爬楼梯的方法。
2. 因而我们`dp[i]`的含义就是，爬到第`i`阶，有多少种爬法。
3. 然后我们要从起点到终点的角度考虑问题，假如我们选择了一次爬`nums[k]`阶以到达第`i`个台阶，那么我们爬楼梯的起点在哪？很显然在`i - nums[k]`的位置。那么我们从台阶的最开始的起点，到`i - nums[k]`处，有多少种爬法？是`dp[i - nums[k]]`，因而，假如我们选择一次爬爬`nums[k]`阶以到达第`i`个台阶，那么我们到第`i`阶的方法数，和我们到第`i - nums[k]`阶的方法数是一样的，因为我们从第`i-nums[k]`阶到第`i`阶只有一种办法——一次爬`nums[k]`阶。
4. 但是`nums[]`有很多个选择啊，所以我们要把每个可行的选择的方法数加起来。（所谓的可行，就是，假如你想去第`i`阶，那么你一次跨越的阶数肯定不能超过`i`，也就是 i - nums[k] >= 0）

第0阶到第0阶肯定只有一种方法，`dp[0] = 0`。

这题和Leetcode 62 63不同的地方就是，62告诉你你一次可以跨越2个或者3个台阶，这里只不过是给了你更多的一次可以跨越台阶数的选项。62其实就是这题的`nums = {2,3}`。

```java
public int combinationSum4(int[] nums, int target) {
    int[] dp = new int[target + 1];
    dp[0] = 1;
    for (int i = 1; i <= target ; i++) {
        for (int num : nums) {
            if (i >= num) {
                dp[i] += dp[i - num];
            }
        }
    }
    return dp[target];
}
```

站在完全背包的角度的理解，等明天脑子没那么晕了再来吧

#### 322. 零钱兑换

给你一个整数数组 `coins` ，表示不同面额的硬币；以及一个整数 `amount` ，表示总金额。

计算并返回可以凑成总金额所需的 **最少的硬币个数** 。如果没有任何一种硬币组合能组成总金额，返回 `-1` 。

你可以认为每种硬币的数量是无限的。



dp[j]：凑足总额为`j`所需钱币的最少个数为`dp[j]`

凑足总额为j - coins[i]的最少个数为dp[j - coins[i]]，那么只需要加上一个钱币coins[i]即dp[j - coins[i]] + 1就是dp[j]（考虑coins[i]）

所以dp[j] 要取所有 `dp[j - coins[i]] + 1` 中最小的。

递推公式：`dp[j] = min(dp[j - coins[i]] + 1, dp[j])`;

#### 279. 完全平方数

给你一个整数 `n` ，返回 *和为 `n` 的完全平方数的最少数量* 。

> 示例 1：
>
> 输入：n = 12
> 输出：3 
> 解释：12 = 4 + 4 + 4
> 示例 2：
>
> 输入：n = 13
> 输出：2
> 解释：13 = 4 + 9

意思就是问你，最少需要几个完全平方数能凑出`n`。



`dp[i]`：和为`i`的完全平方数的最少数量。假如j是一个完全平方数，那么`dp[i] = dp[i - j] + 1`，所以我们要找到最小的`dp[i - j] + 1`。

`dp[0] = 0, dp[1] = 1`。

```java
public int numSquares(int n) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2; i <= n; i ++) {
        for (int j = 1; j * j <= i; j ++) {
            dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
        }
    }
    return dp[n];
}
```

### 139.单词拆分

给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。

`dp[i]`的意义：从`0`到`i`的字符串是否可以被拆分成字典中的单词，是的话`dp[i] = true`。

递推公式：假如我们已知`dp[j] = true`，并且我们已知从`j`到`i`的子串出现在了字典中，那么`dp[i] = true`。

换句话说，如果：从`j`到`i`的子串出现在了字典里，且`dp[j] = true`，那么`dp[i] = true`

确实很像背包问题啊，外层遍历容量，内层遍历物品

```java
public boolean wordBreak(String s, List<String> wordDict) {
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= s.length() ; i++) {
        for (String s1 : wordDict) {
            // i - (j + 1)  + 1= s1.length()
            int j = i - s1.length();
            /*
            s.substring(j, i).equals(s1)
            保证的是在j + 1到i的位置上,确实是s1.
            前两个条件只能保证从0到j是可分的，并不能保证从j+1到i是wordDict中的单词
            */
            if (j >=0 && dp[j] && s.substring(j, i).equals(s1)) {
                dp[i] = true;
                break;
            }
        }
    }
    return dp[s.length()];
}
```

### 多重背包没时间看了，Leetcode上也没有对应的题目

### 198.打家劫舍

你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警**。

给定一个代表每个房屋存放金额的非负整数数组，计算你 **不触动警报装置的情况下** ，一夜之内能够偷窃到的最高金额。



`dp[i]`：从前`i`间房屋偷窃，所能取得的最大金额。我们的房间数从1开始编号。

对于第`i`间房，我们有两种选择：

1. 偷窃它，这意味着与它左右相邻的房间都不能偷。因此`dp[i] = dp[i - 2] + value[i - 1]`。`vaule[i - 1]`是第`i`间房的价值。
2. 不偷窃，这意味着第`i`间房相当于不存在，我们要做的就是从前`i-1`间房里偷取最大的价值， `dp[i] = dp[i - 1]`。
3. 二者取收益较大的那个即可。

```java
public int rob(int[] nums) {
    int[] dp = new int[nums.length + 1];
    dp[0] = 0;
    dp[1] = nums[0];
    for (int i = 2; i < dp.length; i++) {
        // 偷第i间
        int steal = dp[i - 2] + nums[i];
        // 不偷第i间
        int NotSteal = dp[i - 1];
        dp[i] = Math.max(steal, NotSteal);
    }
    return dp[nums.length];
}
```

### 213.打家劫舍II

你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，**这意味着第一个房屋和最后一个房屋是紧挨着的**。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。

给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，能够偷窃到的最高金额。



和打家劫舍I的唯一的不同就是，这里第一个房屋和最后一个房屋是连着的。这意味着第一个房屋的左边是最后一个房屋了。

环状排列，意味着**第一个房子和最后一个房子中，只能选择一个偷窃**，因此可以把此 环状排列房间 问题约化为两个 单排排列房间 子问题：

在不偷窃第一个房子的情况下，最大金额是p1。

在不偷窃最后一个房子的情况下，最大金额是p2。

综合偷窃最大金额： 为以上两种情况的较大值。

```java
public int rob(int[] nums) {
    if (nums.length == 0) {
        return 0;
    }
    if (nums.length == 1) {
        return nums[0];
    }
    return Math.max(RobRange(Arrays.copyOfRange(nums, 0, nums.length - 1)), RobRange(Arrays.copyOfRange(nums, 1, nums.length)));
}
private int RobRange(int[] nums) {
    int[] dp = new int[nums.length + 1];
    dp[0] = 0;
    dp[1] = nums[0];
    for (int i = 2; i < dp.length; i++) {
        dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[- 1 + i]);
    }
    return dp[dp.length - 1];
}
```

### 337.打家劫舍III 没完没了了是吧差不多得了死小偷

小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 `root` 。

除了 `root` 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 **两个直接相连的房子在同一天晚上被打劫** ，房屋将自动报警。

给定二叉树的 `root` 。返回 ***在不触动警报的情况下** ，小偷能够盗取的最高金额* 。

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20210223173849619.png" alt="337.打家劫舍III" style="zoom:80%;" />

纯暴力递归：会超时，还剩俩测试用例过不了

**本题一定是要后序遍历，因为通过递归函数的返回值来做下一步计算**（递归的返回值比大小啊要）。

```java
public int rob(TreeNode root) {
    return RobMax(root);
}

private int RobMax(TreeNode root) {
    if (root == null) {
        return 0;
    }
    if (root.left == null && root.right == null) {
        return root.val;
    }
    // 如果抢劫根节点,那就不能抢劫左右孩子节点,直接跳过去。到左孩子的俩孩子，和右孩子的俩孩子
    int L = 0, R = 0;
    if (root.left != null) {
        L = RobMax(root.left.left) + RobMax(root.left.right);
    }
    if (root.right != null) {
        R = RobMax(root.right.left) + RobMax(root.right.right);
    }
    int RobRoot = root.val + L + R;

    int NotRob = RobMax(root.left) + RobMax(root.right);
    return Math.max(RobRoot, NotRob);
}
```

这个递归的过程中其实是有重复计算了。我们计算了root的四个孙子（左右孩子的孩子）为头结点的子树的情况，又计算了root的左右孩子为头结点的子树的情况，计算左右孩子的时候其实又把孙子计算了一遍。

所以可以用一个Map把计算的结果存下来，这样如果计算过孙子了，那么计算孩子的时候可以复用孙子节点的结果。

也就是，记忆化搜索。

```java
HashMap<TreeNode, Integer> map;
public int rob(TreeNode root) {
    map = new HashMap<>();
    return RobMax(root);
}

private int RobMax(TreeNode root) {
    if (root == null) {
        return 0;
    }
    if (map.containsKey(root)) {
        return  map.get(root);
    }
    if (root.left == null && root.right == null) {
        return root.val;
    }
    // 如果抢劫根节点,那就不能抢劫左右孩子节点,直接跳过去
    int L = 0, R = 0;
    if (root.left != null) {
        L = RobMax(root.left.left) + RobMax(root.left.right);
    }
    if (root.right != null) {
        R = RobMax(root.right.left) + RobMax(root.right.right);
    }
    int RobRoot = root.val + L + R;

    int NotRob = RobMax(root.left) + RobMax(root.right);
    int max = Math.max(RobRoot, NotRob);
    map.put(root, max);
    return max;
}
```



递归加动态规划：树形dp。

我们使用一个长度为2的数组，来记录，一个节点 偷与不偷的两个状态所得到的金钱，那么返回值就是一个长度为2的数组。

因此`dp`的含义是：下标为0记录不偷该节点所得到的的最大金钱，下标为1记录偷该节点所得到的的最大金钱。

同样还是使用后序遍历，因为我们还是需要先得到左右孩子的结果，然后再去作比较，取收益大的那个。



状态转移：

1. 如果偷当前节点，那么左右孩子就不能偷。此时我们的收益是：根节点的值 + 不偷左孩子的最大收益 + 不偷右孩子的最大收益。
2. 如果不偷当前节点，那么可以偷左右孩子，此时的最大收益是：`max(left[0], left[1]) + max(right[0], right[1])`
3. 最后我们`rerturn`的就是由1和2得到的值构成的一个数组。

```

```

<!--以下是2023/09/22-->

### **121**. 买卖股票的最佳时机

给定一个数组 `prices` ，它的第 `i` 个元素 `prices[i]` 表示一支给定股票第 `i` 天的价格。

你只能选择 **某一天** 买入这只股票，并选择在 **未来的某一个不同的日子** 卖出该股票。设计一个算法来计算你所能获取的最大利润。

返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 `0` 



暴力算法无非就是一层循环穷举买入的时间，内层循环穷举卖出的时间。然后找出最大利润。



贪心，因为买卖只进行一次，所以我们取尽量靠左的最小值，和尽量靠右的最大值，得到最大的利润。

```java
public int maxProfit(int[] prices) {
    int Low = prices[0], Max = 0;
    for (int price : prices) {
        Low = Math.min(price, Low);
        Max = Math.max(Max, price - Low);
    }
    return Max;
}
```



动态规划：

`dp[i][0]`：表示第i天不持有股票所得最多现金。`dp[i][1]` 表示第i天持有股票所得最多现金。

状态转移：

1. 第i天不持有股票有两种可能：前一天就不持有，此时今天持有的现金和前一天一样，此时`dp[i][0] = dp[i - 1][0]`。或者前一天持有股票，然后今天把持有的股票卖了： `dp[i][0] = dp[i][1] + price[i]`。二者取收益最大的
2. 第i天持有股票也有两种可能：前一天就持有，且今天不卖出，此时今天和前一天持有的现金一样：`dp[i][1] = dp[i - 1][1]`。或者当天买入：所得现金就是买入今天的股票后所得现金即：`-prices[i]`。二者取收益较大者

最终我们的最大收益是`dp[prices.lenght - 1][0]`。因为我们的股票不能砸在手里，不能套牢，必须卖出去。

```java
public int maxProfit(int[] prices) {
    // dp[i][0]代表当天不持有股票,dp[i][1]代表当天持有股票
    int[][] dps = new int[prices.length][2];
    dps[0][0] = 0;
    dps[0][1] = -prices[0];
    for (int i = 1; i < dps.length; i ++) {
        dps[i][0] = Math.max(dps[i - 1][0], dps[i - 1][1] + prices[i]);
        dps[i][1] = Math.max(dps[i - 1][1], - prices[i]);
    }
    return dps[prices.length - 1][0];
}
```



### **122**. 买卖股票的最佳时机 II

给你一个整数数组 `prices` ，其中 `prices[i]` 表示某支股票第 `i` 天的价格。

在每一天，你可以决定是否购买和/或出售股票。你在任何时候 **最多** 只能持有 **一股** 股票。你也可以先购买，然后在 **同一天** 出售。

返回 *你能获得的 **最大** 利润* 。



`dp[i][]`的意义：`dp[i][0]`：第i天不持有股票，所得最多利润。`dp[i][1]`：第i天持有股票，所得最多利润。

状态转移：

1. 第i天不持有股票有两种可能：前一天就不持有股票，今天保持现状，`dp[i][0] = dp[i - 1][0]`。或者是前一天持有股票，但是今天卖出，`dp[i][0] = dp[i - 1][1] + prices[i]`，即昨天持有股票的现金，加上今天卖出股票的收入。二者取收益较大者
2. 第i天持有股票有两种可能：前一天就持有股票，今天保持现状，`dp[i][1] = dp[i - 1][1]`。或者前一天不持有股票，但是今天买入，`dp[i][1] = dp[i - 1][0] - prices[i]`。即昨天不持有股票的时候的现金，减去今天买入的花费。取收益较大者。

最后结果肯定是`dp[i][0]`。因为我们的股票不能砸在手里，不能套牢，必须卖出去。

```java
    public int maxProfit(int[] prices) {
        // dp[i][0] 表示第i天不持有股票能获得的最大利润
        // dp[i][1] 表示第i天持有股票能获得的最大利润
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < dp.length; i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[prices.length - 1][0];
    }
```



### **123**.买卖股票的最佳时机III

给定一个数组，它的第 `i` 个元素是一支给定的股票在第 `i` 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 **两笔** 交易。

**注意：**你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

和前几题的思路是类似的，但是还是挺绕的。

```java
public int maxProfit(int[] prices) {
    int len = prices.length;
    /*
     * 定义 5 种状态:
     * 0: 没有操作, 1: 第一次买入, 2: 第一次卖出, 3: 第二次买入, 4: 第二次卖出
     */
    int[][] dp = new int[len][5];
    dp[0][1] = -prices[0];
    // 初始化第二次买入的状态是确保 最后结果是最多两次买卖的最大利润
    dp[0][3] = -prices[0];
    for (int i = 1; i < len; i++) {
        // 第一次买入状态:沿用前一天的状态。或者之前是什么都没干的，所以是-prices[i]
        dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);

        // 第一次卖出状态:第i天没有操作，沿用前一天卖出股票的状态，即：dp[i][2] = dp[i - 1][2]。
        // 或者前一天是第一次买入, 第i天卖出股票了，那么dp[i][2] = dp[i - 1][1] + prices[i]
        dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);

        // 第二次买入状态: 沿用前一天的第二次买入状态, 或者前一天是第一次卖出,然后今天买入第二次
        dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);

        // 第二次卖出状态: 沿用前一天的第二次卖出状态(今天什么都不做), 或者前一天是第二次买入,然后今天卖出第二次。
        dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
    }

    return dp[len - 1][4];
}
```

### 188. 买卖股票的最佳时机 IV

给你一个整数数组 `prices` 和一个整数 `k` ，其中 `prices[i]` 是某支给定的股票在第 `i` 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 `k` 笔交易。也就是说，你最多可以买 `k` 次，卖 `k` 次。

把前一天的数组扩展为k倍。

使用二维数组 `dp[i][j]` ：第i天的状态为j，所剩下的最大现金是dp[i][j]

j的状态表示为：

0 表示不操作
1 第一次买入
2 第一次卖出
3 第二次买入
4 第二次卖出
.....
大家应该发现规律了吧 ，除了0以外，偶数就是卖出，奇数就是买入。

题目要求是至多有K笔交易，那么j的范围就定义为 2 * k + 1 就可以了。

### **309**. 买卖股票的最佳时机含冷冻期

给定一个整数数组`prices`，其中第 `prices[i]` 表示第 `*i*` 天的股票价格 。

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

- 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。



这里对于每一天，总共有四个可能的状态：

1. 持有股票状态：可以沿用前一天买入股票的状态、可以今天买入股票
2. 不持有股票状态：可以分为两类
   1. 保持卖出股票的状态（两天前就卖出了股票，度过一天冷冻期。或者是前一天就是卖出股票状态，一直没操作）
   2. 前一天持有股票，今天卖出股票
3. 今天是冷冻期。

注意这里的每一个状态，例如状态一，是持有股票股票状态并不是说今天一定就买入股票，而是说保持买入股票的状态即：可能是前几天买入的，之后一直没操作，所以保持买入股票的状态。



状态转移：

到达状态一：沿用前一天的状态：`dp[i - 1][0]`。或者是今天买入：前一天冷冻期，然后今天买入，`dp[i - 1][3] - prices[i]`。前一天是普通的卖出状态，然后今天买入：`dp[i - 1][1] - prices[i]`

到达状态二：沿用前一天的状态二：`dp[i - 1][1]`。前一天是冷冻期：`dp[i - 3][3]`

到达状态三：只有前一天持有，今天卖出。`dp[i - 1][0] + prices[i]`

到达状态四：只有前一天卖出，今天冷冻期。`dp[i - 1][2]`

**因为不持有股票的状态总共有三个，所以我们要从这三个不持有股票的状态里取最大值。**

```c++
dp[i][0] = max(dp[i - 1][0], max(dp[i - 1][3], dp[i - 1][1]) - prices[i]);
dp[i][1] = max(dp[i - 1][1], dp[i - 1][3]);
dp[i][2] = dp[i - 1][0] + prices[i];
dp[i][3] = dp[i - 1][2];
```

```java
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][4];
        dp[0][0] = - prices[0];
        dp[0][1] = 0;
        dp[0][2] = 0;
        dp[0][3] = 0;
        for (int i = 1; i < dp.length; i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][3], dp[i - 1][1]) - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][3]);
            dp[i][2] = dp[i - 1][0] + prices[i];
            dp[i][3] = dp[i - 1][2];
        }
        return Math.max(dp[prices.length - 1][1], Math.max(dp[prices.length - 1][2], dp[prices.length - 1][3]));
    }
```

### 714. 买卖股票的最佳时机含手续费

给定一个整数数组 `prices`，其中 `prices[i]`表示第 `i` 天的股票价格 ；整数 `fee` 代表了交易股票的手续费用。

你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。

返回获得利润的最大值。

**注意：**这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。



对于每一天，有两个状态：持有股票的状态`dp[i][0]`、以及不持有股票的状态`dp[i][1]`。

状态转移：

- 持有股票的状态：沿用前一天的相同状态，今天什么都不做，`dp[i - 1][0]`。或者今天买入，前一天是不持有的状态：`dp[i - 1][1] - prices[i]` 。手续费我们统一在卖出股票的时候支付。
- 不持有股票的状态：沿用前一天的状态，今天什么都不做，`dp[i - 1][1]`。前一天持有，今天卖出：`dp[i - 1][0] + prices[i] - fee`。
- `dp[0][0] = -prices[i]; dp[0][1] = 0`

```java
    public int maxProfit(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < dp.length; i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
        }
        return dp[prices.length - 1][1];
    }
```

### 300.最长递增子序列

给你一个整数数组 `nums` ，找到其中最长严格递增子序列的长度。

**子序列** 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，`[3,6,2,7]` 是数组 `[0,3,1,6,2,2,7]` 的子序列。

`dp[i]`的定义：以`nums[i]`结尾的，从`nums[0]`到`nums[i]`，所构成的最长严格递增子序列的长度。

状态转移：

对于每个`j < i`，如果`nums[i] > nums[j]`，`dp[i] = max(dp[i], dp[j] + 1)`

dp[i]的初始化：每一个i，对应的dp[i]（即最长递增子序列）起始大小至少都是1。

唯一需要注意的是，题目要求的是最长递增子序列，而不是“以`nums[nums.length - 1]`结尾的最长递增子序列”，所以末尾不要直接`return dp[nums.length - 1]`

```java
public int lengthOfLIS(int[] nums) {
    int[] dp = new int[nums.length];
    int Max = 1;
    Arrays.fill(dp, 1);
    for (int i = 1; i < nums.length; i ++) {
        for (int k = 0; k < i; k ++) {
            if (nums[i] > nums[k]) {
                dp[i] = Math.max(dp[i], dp[k] +1);
                Max = Math.max(dp[i], Max);
            }
        }
    }
    return Max;
}
```

### 674. 最长连续递增序列

给定一个未经排序的整数数组，找到最长且 **连续递增的子序列**，并返回该序列的长度。

**连续递增的子序列** 可以由两个下标 `l` 和 `r`（`l < r`）确定，如果对于每个 `l <= i < r`，都有 `nums[i] < nums[i + 1]` ，那么子序列 `[nums[l], nums[l + 1], ..., nums[r - 1], nums[r]]` 就是连续递增子序列。



这道题相比上一题，就是 不需要遍历从0到`i-1`遍历k了，只需要看`i - 1`。

`dp[i]`的定义：以`nums[i]`结尾的，从`nums[0]`到`nums[i]`，的最长连续递增子序列的长度。

状态转移：对于`dp[i]`，如果`nums[i] > nums[i - 1]`，那么`dp[i] = dp[i - 1] +1`。否则`dp[i] = dp[i - 1]`

初始化同样是`dp[i] = 1`，因为长度为1的子序列一定是连续且严格递增的。



```java
public int findLengthOfLCIS(int[] nums) {
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);
    int max = 1;
    for (int i = 1; i < dp.length; i++) {
        dp[i] = nums[i] > nums[i - 1] ? dp[i - 1] + 1 : dp[i];
        max = Math.max(dp[i], max);
    }
    return max;
}
```



### 718. 最长重复子数组

给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。

> 示例：
>
> 输入：
>
> A: [1,2,3,2,1]
>
> B: [3,2,1,4,7]
>
> 输出：3
>
> 解释：长度最长的公共子数组是 [3, 2, 1] 。

以前只有一个数组的时候，我们的dp数组也就只需要一维就可以了，现在有两个数组了，所以我们的dp数组，也自然地需要两个维度。



`dp[i][j]` ：以`i - 1`为结尾的A，和以下标`j - 1`为结尾的B，最长重复子数组长度为`dp[i][j]`。 （**特别注意**： “以下标i - 1为结尾的A” 标明一定是 以A[i-1]为结尾的字符串 ）

状态转移：当`A[i - 1]` 和`B[j - 1]`相等的时候，`dp[i][j] = dp[i - 1][j - 1] + 1`

`dp[i][0]` 和`dp[0][j]`都初始化为0。

```java
public int findLength(int[] nums1, int[] nums2) {
    int[][] dp = new int[nums1.length + 1][nums2.length + 1];
    dp[0][0] = 0;
    int Max = 0;
    for (int i = 1; i <= nums1.length; i++) {
        for (int j = 1; j <= nums2.length; j++) {
            if (nums2[j] == nums1[i]) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            }
            Max = Math.max(Max, dp[i][j]);
        }
    }
    return Max;
}
```



### **1143**.最长公共子序列

给定两个字符串 `text1` 和 `text2`，返回这两个字符串的最长 **公共子序列** 的长度。如果不存在 **公共子序列** ，返回 `0` 。

一个字符串的 **子序列** 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。

- 例如，`"ace"` 是 `"abcde"` 的子序列，但 `"aec"` 不是 `"abcde"` 的子序列。

两个字符串的 **公共子序列** 是这两个字符串所共同拥有的子序列。



`dp[i][j]`：以`text1[i - 1]`结尾的字符串，和以`text2[j - 1]`结尾的字符串，的最长公共子序列的长度。

`dp[i][j] =  text1[i - 1] == text2[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1])`

如果text1[i - 1] 与 text2[j - 1]相同，那么找到了一个公共元素，所以`dp[i][j] = dp[i - 1][j - 1] + 1;`

如果text1[i - 1] 与 text2[j - 1]不相同，那就看看text1[0, i - 2]与text2[0, j - 1]的最长公共子序列 和 text1[0, i - 1]与text2[0, j - 2]的最长公共子序列，取最大的。

注意一下，状态转移公式不是下面这个：

```java
dp[i][j] =  text1[i - 1] == text2[j - 1] ? dp[i - 1][j - 1] + 1 : dp[i - 1][j - 1];
```

```java
    public int longestCommonSubsequence(String text1, String text2) {
        char[] t2 = text2.toCharArray();
        char[] t1 = text1.toCharArray();
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        dp[0][0] = 0;
        int max = 0;
        for (int i = 1; i < dp.length; i ++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] =  t1[i - 1] == t2[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
```

### 1035.不相交的线

在两条独立的水平线上按给定的顺序写下 `nums1` 和 `nums2` 中的整数。

现在，可以绘制一些连接两个数字 `nums1[i]` 和 `nums2[j]` 的直线，这些直线需要同时满足满足：

-  `nums1[i] == nums2[j]`
- 且绘制的直线不与任何其他连线（非水平线）相交。

请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。

以这种方法绘制线条，并返回可以绘制的最大连线数。



思想和1143是类似的。

因为给两个数组了，所以dp数组要用二维的，一个代表`nums1`，另外一个代表`nums2`。

`dp[i][j]`表示，以`nums1[i - 1]`和`nums2[j - 1]`结尾的子数组，可以绘制的最大连线数。

状态转移：和前一道题可以说一模一样。

需要注意的点也是一模一样：`nums1[i - 1] != nums2[j - 1]`的时候，`dp[i][j]`不是等于`dp[i - 1][j - 1]`

```java
if (nums1[i - 1] == nums2[j - 1]) {
    dp[i][j] = dp[i - 1][j - 1] + 1;
}else {
    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
}
```

```java
public int maxUncrossedLines(int[] nums1, int[] nums2) {
    int[][] dp = new int[nums1.length + 1][nums2.length + 1];
    int max = 0;
    for (int i = 1; i < dp.length; i++) {
        for (int j = 1; j < dp[0].length; j++) {
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            }else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
            max = Math.max(dp[i][j], max);
        }

    }
    return max;
}
```

### 53. 最大子数组和

给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

`dp[i]`：包括下标i（以`nums[i]`为结尾）的最大连续子序列和为`dp[i]`。

状态转移：
dp[i]只有两个方向可以推出来：

- `dp[i - 1] + nums[i]`，即：nums[i]加入当前连续子序列和

- `nums[i]`，即：从头开始计算当前连续子序列和
- 一定是取最大的，所以`dp[i] = max(dp[i - 1] + nums[i], nums[i])`

初始化的时候其实只需要把`dp[0] = nums[0]`即可。

状态转移公式不是：`dp[i] = nums[i] >= 0 ?  dp[i - 1] + nums[i] : dp[i - 1]` 

```java
if (nums[i] >= 0) {
dp[i] = dp[i - 1] + nums[i];
}else {
dp[i] = dp[i - 1];
}
```



```java
public int maxSubArray(int[] nums) {
    int[] dp = new int[nums.length];
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < dp.length; i++) {
        dp[i] = nums[i];
        max = Math.max(max, dp[i]);
    }
    for (int i = 1; i < dp.length; i++) {
        dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        max = Math.max(max, dp[i]);
    }
    return max;
}
```

### 392. 判断子序列

给定字符串 **s** 和 **t** ，判断 **s** 是否为 **t** 的子序列。

字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，`"ace"`是`"abcde"`的一个子序列，而`"aec"`不是）。



这道题的解法是：如果字符串s和t的最长公共子序列的长度，和s的长度相同，那么就说明s是t的一个子序列。

所以我们可以先算s和t的最长公共子序列长度，然后再判定最长长度是否与s相同。

```java
public boolean isSubsequence(String s, String t) {
    char[] t2 = t.toCharArray();
    char[] t1 = s.toCharArray();
    int[][] dp = new int[s.length() + 1][t.length() + 1];
    dp[0][0] = 0;
    int max = 0;
    for (int i = 1; i < dp.length; i ++) {
        for (int j = 1; j < dp[0].length; j++) {
            dp[i][j] =  t1[i - 1] == t2[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
            max = Math.max(max, dp[i][j]);
        }
    }
    return max == s.length();
}
```

也可以用双指针：

初始化两个指针 `i` 和 `j`，分别指向 s 和 t 的初始位置。每次贪心地匹配，匹配成功则 i 和 j 同时右移，匹配 s 的下一个位置，匹配失败则 j 右移，i 不变，尝试用 t 的下一个字符匹配 s。

最终如果  移动到 s 的末尾，就说明 s 是 t 的子序列。

```java
public boolean isSubsequence(String s, String t) {
    char[] chars = s.toCharArray();
    int j = 0;
    char[] chars1 = t.toCharArray();
    for (int i = 0; i < chars1.length && j < chars.length; i ++) {
        if (chars1[i] == chars[j]) {
            j ++;
        }
    }
    return j == chars.length;
}
```

### 115.不同的子序列

给你两个字符串 `s` 和 `t` ，统计并返回在 `s` 的 **子序列** 中 `t` 出现的个数，结果需要对 109 + 7 取模。

> 示例 1：
>
> 输入：s = "rabbbit", t = "rabbit"
> 输出：3
> 解释：
> 如下所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
> [rab]b[bit]
> [rabb]b[it]
> [ra]b[bbit]



`dp[i][j]`的含义：以`s[i - 1]`结尾的子串的子序列中，`t[0:j - 1]`出现的次数。



状态转移公式：

先不扣初始化的细节，假设`dp[i][j]` 就是`s[i]` 和`t[j]` 索引的元素子序列数量

为啥状态方程是： 

1. `s[i] == t[j]` 时 `dp[i][j] = dp[i-1][j-1] + dp[i-1][j]`
2. ``s[i] != t[j]` 时 `dp[i][j] = dp[i-1][j]`

先看`s[i] == t[j]` 时，以`s = "rara" , t = "ra"` 为例，当`i = 3, j = 1`时，`s[i] == t[j]`。

此时分为2种情况，s串用最后一位的a + 不用最后一位的a。

如果用s串最后一位的a,那么t串最后一位的a也被消耗掉，此时的子序列其实=`dp[i-1][j-1]`

如果不用s串最后一位的a，那就得看"rar"里面是否有"ra"子序列的了，就是`dp[i-1][j]`

所以 `dp[i][j] = dp[i-1][j-1] + dp[i-1][j]`

再看`s[i] != t[j]` 比如 `s = "rarb", t = "ra"` 还是当`i = 3, j = 1`时，`s[i] != t[j]`。

此时显然最后的b想用也用不上啊。所以只能指望前面的`"rar"`里面是否有能匹配`"ra"`的。

所以此时`dp[i][j] = dp[i-1][j]`



dp数组的初始化：

`dp[i][0]` 表示：以i-1为结尾的s可以随便删除元素，出现空字符串的个数。0对应的索引是-1，所以看做是空串。

那么`dp[i][0]`一定都是1，因为也就是把以`s[i-1]`为结尾的s，删除所有元素，出现空字符串的个数就是1。

再来看`dp[0][j]`，`dp[0][j]`：空字符串s可以随便删除元素，出现以`t[j-1]`为结尾的字符串t的个数。

那么`dp[0][j]`一定都是0，空串s如论如何也变成不了**非空串**的t。

`dp[0][0]`特殊一点，因为此时s和t都是空串，`dp[0][0]`应该是1，空字符串s，可以删除0个元素，变成空字符串t。

```java
public int numDistinct(String s, String t) {
    int[][] dp = new int[s.length() + 1][t.length() + 1];
    for (int i = 1; i < dp.length; i++) {
        dp[i][0] = 1;
    }
    for (int i = 1; i < dp[0].length; i++) {
        dp[0][i] = 0;
    }
    dp[0][0] = 1;
    for (int i = 1; i < dp.length; i++) {
        for (int j = 1; j < dp[0].length; j++) {
            dp[i][j] = (s.charAt(i - 1) == t.charAt(j - 1)) ? dp[i - 1][j - 1] + dp[i - 1][j] : dp[i - 1][j];
        }
    }
    return dp[s.length()][t.length()];
}
```

### 583. 两个字符串的删除操作

给定两个单词 `word1` 和 `word2` ，返回使得 `word1` 和 `word2` **相同**所需的**最小步数**。

**每步** 可以删除任意一个字符串中的一个字符。



每步只能从一个字符串中删除一个字符。也就是说，a和b需要两步才能变成相同的字符串（空串）。

> 示例 1：
>
> 输入: word1 = "sea", word2 = "eat"
> 输出: 2
> 解释: 第一步将 "sea" 变为 "ea" ，第二步将 "eat "变为 "ea"

`dp[i][j]`的意义：以`word1[i - 1]`结尾的字符串S1，和以`word2[j - 1]`结尾的字符串S2，使得S1和S2相同所需的最小步数。

状态转移：对于`dp[i][j]`

1. 如果`word1[i - 1] == word2[j - 1]`，那么我们只需要考虑最少需要多少步，可以把`word1[0:i - 2]`和`word2[0 : j - 2]`变成相同（这个最小步数就是`dp[i - 1][j - 1]`）。然后因为`word1[i - 1] == word2[j - 1]`，所以各自的最后一个字符都不需要删除，所以就和把`word1[0:i - 2]`和`word2[0 : j - 1]`变成相同，所需的步数相同。因此 `dp[i][j] = dp[i - 1][j - 1]`。
2. 如果`word1[i - 1] != word2[j - 1]`。那么我们有两个方向可以把`word1[0:i - 1]`和`word2[0 : j - 1]`变成相同：
   1. 先把`word1[0:i - 2]`和`word2[0 : j - 1]`变成相同。然后删掉`word1[ i - 1]`
   2. 先把`word1[0:i - 1]`和`word2[0 : j - 2]`变成相同。然后删掉`word2[j - 1]`。
   3. 二者里选较小者。`dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1])`

```java
public int minDistance(String word1, String word2) {
    int[][] dp = new int[word1.length() + 1][word2.length() + 1];
    dp[0][0] = 0;
    for (int i = 1; i < dp.length; i++) {
        dp[i][0] = i;
    }
    for (int j = 1; j < dp[0].length; j++) {
        dp[0][j] = j;
    }
    for (int i = 1; i < dp.length; i++) {
        for (int j = 1; j < dp[0].length; j++) {
            char c1 = word1.charAt(i - 1);
            char c2 = word2.charAt(j - 1);
            if (c1 == c2) {
                dp[i][j] = dp[i - 1][j - 1];
            }else {
                int i1 = dp[i - 1][j];
                int i2 = dp[i][j - 1];
                dp[i][j] = Math.min(i1, i2) + 1;
            }
        }
    }
    return dp[word1.length()][word2.length()];
}
```

### **72**. 编辑距离

给你两个单词 `word1` 和 `word2`， *请返回将 `word1` 转换成 `word2` 所使用的最少操作数* 。

你可以对一个单词进行如下三种操作：

- 插入一个字符
- 删除一个字符
- 替换一个字符

> 示例 1：
>
> 输入：word1 = "horse", word2 = "ros"
> 输出：3
> 解释：
> horse -> rorse (将 'h' 替换为 'r')
> rorse -> rose (删除 'r')
> rose -> ros (删除 'e')

不想写了。看代码随想录即可

https://programmercarl.com/0072.%E7%BC%96%E8%BE%91%E8%B7%9D%E7%A6%BB.html#%E6%80%9D%E8%B7%AF

### **647**. 回文子串

给你一个字符串 `s` ，请你统计并返回这个字符串中 **回文子串** 的数目。

**回文字符串** 是正着读和倒过来读一样的字符串。

**子字符串** 是字符串中的由连续字符组成的一个序列。

具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。

> 示例 1：
>
> 输入：s = "abc"
> 输出：3
> 解释：三个回文子串: "a", "b", "c"
> 示例 2：
>
> 输入：s = "aaa"
> 输出：6
> 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"



`dp[i][j]`的意义：以`s[i]`开头，以`s[j]`结尾子串，是否是一个回文子串。也就是说我们这次的dp数组，是一个布尔值的数组。

状态转移：

整体上是两种，就是s[i]与s[j]相等，s[i]与s[j]不相等这两种。

当s[i]与s[j]不相等，那没啥好说的了，`dp[i][j]`一定是false。

当s[i]与s[j]相等时，这就复杂一些了，有如下三种情况

- 情况一：下标i 与 j相同，同一个字符例如a，当然是回文子串
- 情况二：下标i 与 j相差为1，例如aa，也是回文子串
- 情况三：下标：i 与 j相差大于1的时候，例如cabac，此时s[i]与s[j]已经相同了，我们看i到j区间是不是回文子串就看aba是不是回文就可以了，那么aba的区间就是 i+1 与 j-1区间，这个区间是不是回文就看`dp[i + 1][j - 1]`是否为true。



dp数组的遍历顺序上则和一般的二维dp数组从上到下，从左到右逐行遍历，不太一样。

因为`dp[i][j]`在情况3，依赖的是它左下角的 `dp[i + 1][j - 1]`，所以我们要保证它的左下角比它先算出来。

所以我们最好是从下往上遍历每一行，每一行内从左往右遍历。

**注意因为`dp[i][j]`的定义，所以j一定是大于等于i的，那么在填充`dp[i][j]`的时候一定是只填充右上半部分**。

### 516. 最长回文子序列

给你一个字符串 `s` ，找出其中最长的回文子序列，并返回该序列的长度。

子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。



**`dp[i][j]`：字符串s在[i, j]范围内最长的回文子序列的长度为`dp[i][j]`**。

状态转移：

在判断回文子串的题目中，关键逻辑就是看s[i]与s[j]是否相同。

如果`s[i]`与`s[j]`相同，那么`dp[i][j] = dp[i + 1][j - 1] + 2`，就是把一头一尾的两个字符的长度也加进去嘛。

如果`s[i]`与`s[j]`不相同，说明`s[i]`和`s[j]`的同时加入 并不能增加`[i,j]`区间回文子序列的长度，那么分别加入`s[i]`、`s[j]`看看哪一个可以组成最长的回文子序列。

- 加入`s[j]`的回文子序列长度为`dp[i + 1][j]`。

- 加入`s[i]`的回文子序列长度为`dp[i][j - 1]`。
- 那么`dp[i][j]`一定是取最大的，即：`dp[i][j] = max(dp[i + 1][j], dp[i][j - 1])`



初始化：首先要考虑当i 和j 相同的情况，从递推公式：`dp[i][j] = dp[i + 1][j - 1] + 2`; 可以看出，递推公式是计算不到 i 和 j相同时候的情况。所以需要手动初始化一下，当i与j相同，那么`dp[i][j]`一定是等于1的，即：一个字符的回文子序列长度就是1。



**遍历顺序**：遍历顺序真的非常重要，**所以一定不能无脑从上往下逐行，从左往右逐列，一定要看递推公式的依赖关系**。从递归公式中，可以看出，`dp[i][j]` 依赖于 `dp[i + 1][j - 1]` ，`dp[i + 1][j]` 和 `dp[i][j - 1]`，如图：

<img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20230102172155.png" alt="img" style="zoom:50%;" />

所以遍历顺序是从下往上，从左往右的。

并且，**因为`dp[i][j]`的定义，所以j一定是大于等于i的，那么在填充`dp[i][j]`的时候一定是只填充右上半部分**。`j`要从`i + 1`开始。

```java
public int longestPalindromeSubseq(String s) {
    int max = 1;
    int[][] dp = new int[s.length()][s.length()];
    for (int i = 0; i < dp.length; i++) {
        dp[i][i] = 1;
    }
    for (int i = dp.length - 1; i >= 0; i --) {
        for (int j = i + 1; j < dp[0].length; j++) {
            if (s.charAt(i) == s.charAt(j)) {
                dp[i][j] = dp[i + 1][j - 1] + 2;
            }else {
                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
            max = Math.max(max, dp[i][j]);
        }
    }
    return max;
}
```

