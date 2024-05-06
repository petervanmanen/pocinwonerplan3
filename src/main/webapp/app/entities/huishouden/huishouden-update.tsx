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
import { IRelatie } from 'app/shared/model/relatie.model';
import { getEntities as getRelaties } from 'app/entities/relatie/relatie.reducer';
import { IHuishouden } from 'app/shared/model/huishouden.model';
import { getEntity, updateEntity, createEntity, reset } from './huishouden.reducer';

export const HuishoudenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const relaties = useAppSelector(state => state.relatie.entities);
  const huishoudenEntity = useAppSelector(state => state.huishouden.entity);
  const loading = useAppSelector(state => state.huishouden.loading);
  const updating = useAppSelector(state => state.huishouden.updating);
  const updateSuccess = useAppSelector(state => state.huishouden.updateSuccess);

  const handleClose = () => {
    navigate('/huishouden');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
    dispatch(getRelaties({}));
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
      ...huishoudenEntity,
      ...values,
      maaktonderdeeluitvanClients: mapIdList(values.maaktonderdeeluitvanClients),
      maaktonderdeelvanRelaties: mapIdList(values.maaktonderdeelvanRelaties),
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
          ...huishoudenEntity,
          maaktonderdeeluitvanClients: huishoudenEntity?.maaktonderdeeluitvanClients?.map(e => e.id.toString()),
          maaktonderdeelvanRelaties: huishoudenEntity?.maaktonderdeelvanRelaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.huishouden.home.createOrEditLabel" data-cy="HuishoudenCreateUpdateHeading">
            Create or edit a Huishouden
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="huishouden-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Maaktonderdeeluitvan Client"
                id="huishouden-maaktonderdeeluitvanClient"
                data-cy="maaktonderdeeluitvanClient"
                type="select"
                multiple
                name="maaktonderdeeluitvanClients"
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
              <ValidatedField
                label="Maaktonderdeelvan Relatie"
                id="huishouden-maaktonderdeelvanRelatie"
                data-cy="maaktonderdeelvanRelatie"
                type="select"
                multiple
                name="maaktonderdeelvanRelaties"
              >
                <option value="" key="0" />
                {relaties
                  ? relaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/huishouden" replace color="info">
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

export default HuishoudenUpdate;
