from typing import List, Union

from python_modules.datastructure import MyQueue


class Item:
    man = 0
    vegetable = 1
    goat = 2
    wolf = 3


class Status:
    def __repr__(self) -> str:
        return "{0:b}".format(self.left)

    def __init__(self, left: int = 0x0, right: int = 0xF, prev=None):
        self.left: int = left
        self.right: int = right
        self.prev = prev

    def is_man_in_left(self) -> bool:
        return bool(self.left & (0x1 << Item.man))


def get_bit(item: int) -> int:
    return 1 << item


def check(bits: int) -> bool:
    if (bits & get_bit(Item.goat)) and (bits & (get_bit(Item.wolf) | get_bit(Item.vegetable))):
        return False
    else:
        return True


def bits_to_list(bits: int) -> List[str]:
    result: List[str] = []
    if get_bit(Item.man) & bits:
        result.append("man")
    if get_bit(Item.goat) & bits:
        result.append("goat")
    if get_bit(Item.wolf) & bits:
        result.append("wolf")
    if get_bit(Item.vegetable) & bits:
        result.append("vegetables")
    return result


if __name__ == '__main__':
    queue: MyQueue = MyQueue()
    status: Status = Status(0x0, 0xF)
    visited: List[bool] = [False for i in range(0x10)]
    visited[0x0] = True
    queue.push(status)
    result_status: Union[Status, None] = None

    while queue:
        curstatus: Status = queue.pop()
        if curstatus.right == 0x0:
            result_status = curstatus
            break
        bit_man = 0x1 << Item.man
        man_in_left: bool = curstatus.is_man_in_left()
        for i in range(4):
            bit_i = 0x1 << i
            if man_in_left:
                if curstatus.left & bit_i:
                    left = curstatus.left ^ (bit_man | bit_i)
                    right = curstatus.right | (bit_man | bit_i)
                    if not visited[left] and check(left):
                        visited[left] = True
                        next_status: Status = Status(left, right, curstatus)
                        queue.push(next_status)
            else:
                if curstatus.right & bit_i:
                    left = curstatus.left | (bit_man | bit_i)
                    right = curstatus.right ^ (bit_man | bit_i)
                    if not visited[left] and check(right):
                        visited[left] = True
                        next_status: Status = Status(left, right, curstatus)
                        queue.push(next_status)
    move_sequence: List[Status] = []
    while result_status:
        move_sequence.append(result_status)
        result_status = result_status.prev
    move_sequence.reverse()
    print("The items on the other side:")
    for status in move_sequence:
        print(bits_to_list(status.left))
