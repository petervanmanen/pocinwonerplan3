import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILigplaats } from 'app/shared/model/ligplaats.model';
import { getEntity, updateEntity, createEntity, reset } from './ligplaats.reducer';

export const LigplaatsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ligplaatsEntity = useAppSelector(state => state.ligplaats.entity);
  const loading = useAppSelector(state => state.ligplaats.loading);
  const updating = useAppSelector(state => state.ligplaats.updating);
  const updateSuccess = useAppSelector(state => state.ligplaats.updateSuccess);

  const handleClose = () => {
    navigate('/ligplaats');
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
      ...ligplaatsEntity,
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
          ...ligplaatsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ligplaats.home.createOrEditLabel" data-cy="LigplaatsCreateUpdateHeading">
            Create or edit a Ligplaats
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ligplaats-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="ligplaats-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="ligplaats-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="ligplaats-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="ligplaats-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField label="Documentdatum" id="ligplaats-documentdatum" name="documentdatum" data-cy="documentdatum" type="date" />
              <ValidatedField
                label="Documentnummer"
                id="ligplaats-documentnummer"
                name="documentnummer"
                data-cy="documentnummer"
                type="text"
              />
              <ValidatedField label="Geconstateerd" id="ligplaats-geconstateerd" name="geconstateerd" data-cy="geconstateerd" type="text" />
              <ValidatedField label="Geometrie" id="ligplaats-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Identificatie" id="ligplaats-identificatie" name="identificatie" data-cy="identificatie" type="text" />
              <ValidatedField
                label="Inonderzoek"
                id="ligplaats-inonderzoek"
                name="inonderzoek"
                data-cy="inonderzoek"
                check
                type="checkbox"
              />
              <ValidatedField label="Status" id="ligplaats-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Versie" id="ligplaats-versie" name="versie" data-cy="versie" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ligplaats" replace color="info">
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

export default LigplaatsUpdate;
