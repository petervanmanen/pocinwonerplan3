import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';
import { getEntities as getClientbegeleiders } from 'app/entities/clientbegeleider/clientbegeleider.reducer';
import { IInkomensvoorzieningsoort } from 'app/shared/model/inkomensvoorzieningsoort.model';
import { getEntities as getInkomensvoorzieningsoorts } from 'app/entities/inkomensvoorzieningsoort/inkomensvoorzieningsoort.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IInkomensvoorziening } from 'app/shared/model/inkomensvoorziening.model';
import { getEntity, updateEntity, createEntity, reset } from './inkomensvoorziening.reducer';

export const InkomensvoorzieningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientbegeleiders = useAppSelector(state => state.clientbegeleider.entities);
  const inkomensvoorzieningsoorts = useAppSelector(state => state.inkomensvoorzieningsoort.entities);
  const clients = useAppSelector(state => state.client.entities);
  const inkomensvoorzieningEntity = useAppSelector(state => state.inkomensvoorziening.entity);
  const loading = useAppSelector(state => state.inkomensvoorziening.loading);
  const updating = useAppSelector(state => state.inkomensvoorziening.updating);
  const updateSuccess = useAppSelector(state => state.inkomensvoorziening.updateSuccess);

  const handleClose = () => {
    navigate('/inkomensvoorziening');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClientbegeleiders({}));
    dispatch(getInkomensvoorzieningsoorts({}));
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
      ...inkomensvoorzieningEntity,
      ...values,
      emptyClientbegeleider: clientbegeleiders.find(it => it.id.toString() === values.emptyClientbegeleider?.toString()),
      issoortvoorzieningInkomensvoorzieningsoort: inkomensvoorzieningsoorts.find(
        it => it.id.toString() === values.issoortvoorzieningInkomensvoorzieningsoort?.toString(),
      ),
      voorzieningbijstandspartijClients: mapIdList(values.voorzieningbijstandspartijClients),
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
          ...inkomensvoorzieningEntity,
          emptyClientbegeleider: inkomensvoorzieningEntity?.emptyClientbegeleider?.id,
          issoortvoorzieningInkomensvoorzieningsoort: inkomensvoorzieningEntity?.issoortvoorzieningInkomensvoorzieningsoort?.id,
          voorzieningbijstandspartijClients: inkomensvoorzieningEntity?.voorzieningbijstandspartijClients?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inkomensvoorziening.home.createOrEditLabel" data-cy="InkomensvoorzieningCreateUpdateHeading">
            Create or edit a Inkomensvoorziening
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
                <ValidatedField name="id" required readOnly id="inkomensvoorziening-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="inkomensvoorziening-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datumeinde" id="inkomensvoorziening-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="inkomensvoorziening-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumtoekenning"
                id="inkomensvoorziening-datumtoekenning"
                name="datumtoekenning"
                data-cy="datumtoekenning"
                type="text"
              />
              <ValidatedField label="Eenmalig" id="inkomensvoorziening-eenmalig" name="eenmalig" data-cy="eenmalig" check type="checkbox" />
              <ValidatedField
                label="Groep"
                id="inkomensvoorziening-groep"
                name="groep"
                data-cy="groep"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                id="inkomensvoorziening-emptyClientbegeleider"
                name="emptyClientbegeleider"
                data-cy="emptyClientbegeleider"
                label="Empty Clientbegeleider"
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
              <ValidatedField
                id="inkomensvoorziening-issoortvoorzieningInkomensvoorzieningsoort"
                name="issoortvoorzieningInkomensvoorzieningsoort"
                data-cy="issoortvoorzieningInkomensvoorzieningsoort"
                label="Issoortvoorziening Inkomensvoorzieningsoort"
                type="select"
              >
                <option value="" key="0" />
                {inkomensvoorzieningsoorts
                  ? inkomensvoorzieningsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Voorzieningbijstandspartij Client"
                id="inkomensvoorziening-voorzieningbijstandspartijClient"
                data-cy="voorzieningbijstandspartijClient"
                type="select"
                multiple
                name="voorzieningbijstandspartijClients"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inkomensvoorziening" replace color="info">
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

export default InkomensvoorzieningUpdate;
