import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWoonplaats } from 'app/shared/model/woonplaats.model';
import { getEntity, updateEntity, createEntity, reset } from './woonplaats.reducer';

export const WoonplaatsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const woonplaatsEntity = useAppSelector(state => state.woonplaats.entity);
  const loading = useAppSelector(state => state.woonplaats.loading);
  const updating = useAppSelector(state => state.woonplaats.updating);
  const updateSuccess = useAppSelector(state => state.woonplaats.updateSuccess);

  const handleClose = () => {
    navigate('/woonplaats');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...woonplaatsEntity,
      ...values,
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
          ...woonplaatsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.woonplaats.home.createOrEditLabel" data-cy="WoonplaatsCreateUpdateHeading">
            Create or edit a Woonplaats
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="woonplaats-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="woonplaats-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="woonplaats-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="woonplaats-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="woonplaats-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="woonplaats-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Geometrie" id="woonplaats-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField
                label="Identificatie"
                id="woonplaats-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Inonderzoek"
                id="woonplaats-inonderzoek"
                name="inonderzoek"
                data-cy="inonderzoek"
                check
                type="checkbox"
              />
              <ValidatedField label="Status" id="woonplaats-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Versie" id="woonplaats-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField
                label="Woonplaatsnaam"
                id="woonplaats-woonplaatsnaam"
                name="woonplaatsnaam"
                data-cy="woonplaatsnaam"
                type="text"
              />
              <ValidatedField
                label="Woonplaatsnaamnen"
                id="woonplaats-woonplaatsnaamnen"
                name="woonplaatsnaamnen"
                data-cy="woonplaatsnaamnen"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/woonplaats" replace color="info">
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

export default WoonplaatsUpdate;
