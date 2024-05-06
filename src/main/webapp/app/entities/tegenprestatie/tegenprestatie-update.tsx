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
import { ITegenprestatie } from 'app/shared/model/tegenprestatie.model';
import { getEntity, updateEntity, createEntity, reset } from './tegenprestatie.reducer';

export const TegenprestatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const tegenprestatieEntity = useAppSelector(state => state.tegenprestatie.entity);
  const loading = useAppSelector(state => state.tegenprestatie.loading);
  const updating = useAppSelector(state => state.tegenprestatie.updating);
  const updateSuccess = useAppSelector(state => state.tegenprestatie.updateSuccess);

  const handleClose = () => {
    navigate('/tegenprestatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
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
      ...tegenprestatieEntity,
      ...values,
      leverttegenprestatieClient: clients.find(it => it.id.toString() === values.leverttegenprestatieClient?.toString()),
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
          ...tegenprestatieEntity,
          leverttegenprestatieClient: tegenprestatieEntity?.leverttegenprestatieClient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tegenprestatie.home.createOrEditLabel" data-cy="TegenprestatieCreateUpdateHeading">
            Create or edit a Tegenprestatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="tegenprestatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="tegenprestatie-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="tegenprestatie-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                id="tegenprestatie-leverttegenprestatieClient"
                name="leverttegenprestatieClient"
                data-cy="leverttegenprestatieClient"
                label="Leverttegenprestatie Client"
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tegenprestatie" replace color="info">
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

export default TegenprestatieUpdate;
