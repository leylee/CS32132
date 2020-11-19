class MyNode:
    def __init__(self, value=None, prev=None, next=None):
        self.value = value
        self.prev: MyNode = prev
        self.next: MyNode = next


class MyQueue:
    def __init__(self):
        self.size: int = 0
        self.head: MyNode = MyNode()
        self.head.next = self.head
        self.head.prev = self.head

    def __len__(self) -> int:
        return self.size

    def push(self, element) -> None:
        self.size += 1
        new_node = MyNode(element, self.head.prev, self.head)
        self.head.prev.next = new_node
        self.head.prev = new_node

    def pop(self):
        if self:
            self.size -= 1
            node = self.head.next
            self.head.next = node.next
            node.next.prev = self.head
            return node.value
        else:
            return None

    def front(self):
        if self:
            return self.head.next.value
        else:
            return None


class MyStack:
    def __init__(self):
        self.size: int = 0
        self.head: MyNode = MyNode()
        self.head.next = self.head
        self.head.prev = self.head

    def __len__(self) -> int:
        return self.size

    def push(self, element) -> None:
        self.size += 1
        new_node = MyNode(element, self.head.prev, self.head)
        self.head.prev.next = new_node
        self.head.prev = new_node

    def pop(self):
        if self:
            self.size -= 1
            node = self.head.prev
            node.prev.next = self.head
            self.head.prev = node.prev
            return node.value
        else:
            return None

    def top(self):
        if self:
            return self.head.prev.value
        else:
            return None
