    mov EAX 4
    mov EBX 2
    mov ECX 1
    mov EDX 0
L1: div EAX EBX
    add EDX ECX
    jnz EAX L1
    out EDX