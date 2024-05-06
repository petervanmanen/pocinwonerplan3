import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';
import { getEntities as getClientbegeleiders } from 'app/entities/clientbegeleider/clientbegeleider.reducer';
import { IBeschikking } from 'app/shared/model/beschikking.model';
import { getEntity, updateEntity, createEntity, reset } from './beschikking.reducer';

export const BeschikkingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const clientbegeleiders = useAppSelector(state => state.clientbegeleider.entities);
  const beschikkingEntity = useAppSelector(state => state.beschikking.entity);
  const loading = useAppSelector(state => state.beschikking.loading);
  const updating = useAppSelector(state => state.beschikking.updating);
  const updateSuccess = useAppSelector(state => state.beschikking.updateSuccess);

  const handleClose = () => {
    navigate('/beschikking');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
    dispatch(getClientbegeleiders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...beschikkingEntity,
      ...values,
      emptyClient: clients.find(it => it.id.toString() === values.emptyClient?.toString()),
      geeftafClientbegeleider: clientbegeleiders.find(it => it.id.toString() === values.geeftafClientbegeleider?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...beschikkingEntity,
          emptyClient: beschikkingEntity?.emptyClient?.id,
          geeftafClientbegeleider: beschikkingEntity?.geeftafClientbegeleider?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beschikking.home.createOrEditLabel" data-cy="BeschikkingCreateUpdateHeading">
            Create or edit a Beschikking
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="beschikking-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Code"
                id="beschikking-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Commentaar" id="beschikking-commentaar" name="commentaar" data-cy="commentaar" type="text" />
              <ValidatedField label="Datumafgifte" id="beschikking-datumafgifte" name="datumafgifte" data-cy="datumafgifte" type="date" />
              <ValidatedField label="Grondslagen" id="beschikking-grondslagen" name="grondslagen" data-cy="grondslagen" type="text" />
              <ValidatedField label="Wet" id="beschikking-wet" name="wet" data-cy="wet" type="text" />
              <ValidatedField id="beschikking-emptyClient" name="emptyClient" data-cy="emptyClient" label="Empty Client" type="select">
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="beschikking-geeftafClientbegeleider"
                name="geeftafClientbegeleider"
                data-cy="geeftafClientbegeleider"
                label="Geeftaf Clientbegeleider"
                type="select"
              >
                <option value="" key="0" />
                {clientbegeleiders
                  ? clientbegeleiders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beschikking" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BeschikkingUpdate;
