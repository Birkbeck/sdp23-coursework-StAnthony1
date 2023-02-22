    mov EAX 2
    mov EBX 2
    mov ECX 1
L1: add EAX EBX
    sub EBX ECX
    jnz EBX L1
    out EAX