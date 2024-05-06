import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRegelingsoort } from 'app/shared/model/regelingsoort.model';
import { getEntities as getRegelingsoorts } from 'app/entities/regelingsoort/regelingsoort.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IRegeling } from 'app/shared/model/regeling.model';
import { getEntity, updateEntity, createEntity, reset } from './regeling.reducer';

export const RegelingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const regelingsoorts = useAppSelector(state => state.regelingsoort.entities);
  const clients = useAppSelector(state => state.client.entities);
  const regelingEntity = useAppSelector(state => state.regeling.entity);
  const loading = useAppSelector(state => state.regeling.loading);
  const updating = useAppSelector(state => state.regeling.updating);
  const updateSuccess = useAppSelector(state => state.regeling.updateSuccess);

  const handleClose = () => {
    navigate('/regeling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRegelingsoorts({}));
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
      ...regelingEntity,
      ...values,
      isregelingsoortRegelingsoort: regelingsoorts.find(it => it.id.toString() === values.isregelingsoortRegelingsoort?.toString()),
      heeftregelingClient: clients.find(it => it.id.toString() === values.heeftregelingClient?.toString()),
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
          ...regelingEntity,
          isregelingsoortRegelingsoort: regelingEntity?.isregelingsoortRegelingsoort?.id,
          heeftregelingClient: regelingEntity?.heeftregelingClient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.regeling.home.createOrEditLabel" data-cy="RegelingCreateUpdateHeading">
            Create or edit a Regeling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="regeling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumeinde" id="regeling-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="regeling-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumtoekenning"
                id="regeling-datumtoekenning"
                name="datumtoekenning"
                data-cy="datumtoekenning"
                type="text"
              />
              <ValidatedField label="Omschrijving" id="regeling-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="regeling-isregelingsoortRegelingsoort"
                name="isregelingsoortRegelingsoort"
                data-cy="isregelingsoortRegelingsoort"
                label="Isregelingsoort Regelingsoort"
                type="select"
              >
                <option value="" key="0" />
                {regelingsoorts
                  ? regelingsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="regeling-heeftregelingClient"
                name="heeftregelingClient"
                data-cy="heeftregelingClient"
                label="Heeftregeling Client"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/regeling" replace color="info">
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

export default RegelingUpdate;
