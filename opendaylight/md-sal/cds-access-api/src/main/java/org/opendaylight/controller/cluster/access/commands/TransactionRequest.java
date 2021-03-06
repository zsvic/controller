/*
 * Copyright (c) 2016 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.cluster.access.commands;

import akka.actor.ActorRef;
import com.google.common.annotations.Beta;
import org.opendaylight.controller.cluster.access.ABIVersion;
import org.opendaylight.controller.cluster.access.concepts.Request;
import org.opendaylight.controller.cluster.access.concepts.RequestException;
import org.opendaylight.controller.cluster.access.concepts.TransactionIdentifier;

/**
 * Abstract base class for {@link Request}s involving specific transaction. This class is visible outside of this
 * package solely for the ability to perform a unified instanceof check.
 *
 * @author Robert Varga
 *
 * @param <T> Message type
 */
@Beta
public abstract class TransactionRequest<T extends TransactionRequest<T>> extends Request<TransactionIdentifier, T> {
    private static final long serialVersionUID = 1L;

    TransactionRequest(final TransactionIdentifier identifier, final ActorRef replyTo) {
        super(identifier, replyTo);
    }

    TransactionRequest(final T request, final ABIVersion version) {
        super(request, version);
    }

    @Override
    public final TransactionFailure toRequestFailure(final RequestException cause) {
        return new TransactionFailure(getTarget(), cause);
    }

    @Override
    protected abstract AbstractTransactionRequestProxy<T> externalizableProxy(final ABIVersion version);
}
