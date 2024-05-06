import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFraudesoort } from 'app/shared/model/fraudesoort.model';
import { getEntities as getFraudesoorts } from 'app/entities/fraudesoort/fraudesoort.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IFraudegegevens } from 'app/shared/model/fraudegegevens.model';
import { getEntity, updateEntity, createEntity, reset } from './fraudegegevens.reducer';

export const FraudegegevensUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fraudesoorts = useAppSelector(state => state.fraudesoort.entities);
  const clients = useAppSelector(state => state.client.entities);
  const fraudegegevensEntity = useAppSelector(state => state.fraudegegevens.entity);
  const loading = useAppSelector(state => state.fraudegegevens.loading);
  const updating = useAppSelector(state => state.fraudegegevens.updating);
  const updateSuccess = useAppSelector(state => state.fraudegegevens.updateSuccess);

  const handleClose = () => {
    navigate('/fraudegegevens');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFraudesoorts({}));
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
      ...fraudegegevensEntity,
      ...values,
      isvansoortFraudesoort: fraudesoorts.find(it => it.id.toString() === values.isvansoortFraudesoort?.toString()),
      heeftfraudegegevensClient: clients.find(it => it.id.toString() === values.heeftfraudegegevensClient?.toString()),
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
          ...fraudegegevensEntity,
          isvansoortFraudesoort: fraudegegevensEntity?.isvansoortFraudesoort?.id,
          heeftfraudegegevensClient: fraudegegevensEntity?.heeftfraudegegevensClient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.fraudegegevens.home.createOrEditLabel" data-cy="FraudegegevensCreateUpdateHeading">
            Create or edit a Fraudegegevens
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
                <ValidatedField name="id" required readOnly id="fraudegegevens-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bedragfraude"
                id="fraudegegevens-bedragfraude"
                name="bedragfraude"
                data-cy="bedragfraude"
                type="text"
              />
              <ValidatedField
                label="Datumeindefraude"
                id="fraudegegevens-datumeindefraude"
                name="datumeindefraude"
                data-cy="datumeindefraude"
                type="text"
              />
              <ValidatedField
                label="Datumgeconstateerd"
                id="fraudegegevens-datumgeconstateerd"
                name="datumgeconstateerd"
                data-cy="datumgeconstateerd"
                type="text"
              />
              <ValidatedField
                label="Datumstartfraude"
                id="fraudegegevens-datumstartfraude"
                name="datumstartfraude"
                data-cy="datumstartfraude"
                type="text"
              />
              <ValidatedField label="Verrekening" id="fraudegegevens-verrekening" name="verrekening" data-cy="verrekening" type="text" />
              <ValidatedField label="Vorderingen" id="fraudegegevens-vorderingen" name="vorderingen" data-cy="vorderingen" type="text" />
              <ValidatedField
                id="fraudegegevens-isvansoortFraudesoort"
                name="isvansoortFraudesoort"
                data-cy="isvansoortFraudesoort"
                label="Isvansoort Fraudesoort"
                type="select"
              >
                <option value="" key="0" />
                {fraudesoorts
                  ? fraudesoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="fraudegegevens-heeftfraudegegevensClient"
                name="heeftfraudegegevensClient"
                data-cy="heeftfraudegegevensClient"
                label="Heeftfraudegegevens Client"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fraudegegevens" replace color="info">
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

export default FraudegegevensUpdate;
