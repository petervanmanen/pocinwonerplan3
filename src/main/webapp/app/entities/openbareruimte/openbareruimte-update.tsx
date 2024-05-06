import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOpenbareruimte } from 'app/shared/model/openbareruimte.model';
import { getEntity, updateEntity, createEntity, reset } from './openbareruimte.reducer';

export const OpenbareruimteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const openbareruimteEntity = useAppSelector(state => state.openbareruimte.entity);
  const loading = useAppSelector(state => state.openbareruimte.loading);
  const updating = useAppSelector(state => state.openbareruimte.updating);
  const updateSuccess = useAppSelector(state => state.openbareruimte.updateSuccess);

  const handleClose = () => {
    navigate('/openbareruimte');
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
      ...openbareruimteEntity,
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
          ...openbareruimteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.openbareruimte.home.createOrEditLabel" data-cy="OpenbareruimteCreateUpdateHeading">
            Create or edit a Openbareruimte
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
                <ValidatedField name="id" required readOnly id="openbareruimte-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="openbareruimte-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="openbareruimte-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="openbareruimte-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="openbareruimte-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="openbareruimte-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Geometrie" id="openbareruimte-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField
                label="Huisnummerrangeevenenonevennummers"
                id="openbareruimte-huisnummerrangeevenenonevennummers"
                name="huisnummerrangeevenenonevennummers"
                data-cy="huisnummerrangeevenenonevennummers"
                type="text"
              />
              <ValidatedField
                label="Huisnummerrangeevennummers"
                id="openbareruimte-huisnummerrangeevennummers"
                name="huisnummerrangeevennummers"
                data-cy="huisnummerrangeevennummers"
                type="text"
              />
              <ValidatedField
                label="Huisnummerrangeonevennummers"
                id="openbareruimte-huisnummerrangeonevennummers"
                name="huisnummerrangeonevennummers"
                data-cy="huisnummerrangeonevennummers"
                type="text"
              />
              <ValidatedField
                label="Identificatie"
                id="openbareruimte-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Inonderzoek"
                id="openbareruimte-inonderzoek"
                name="inonderzoek"
                data-cy="inonderzoek"
                check
                type="checkbox"
              />
              <ValidatedField label="Labelnaam" id="openbareruimte-labelnaam" name="labelnaam" data-cy="labelnaam" type="text" />
              <ValidatedField
                label="Naamopenbareruimte"
                id="openbareruimte-naamopenbareruimte"
                name="naamopenbareruimte"
                data-cy="naamopenbareruimte"
                type="text"
              />
              <ValidatedField label="Status" id="openbareruimte-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Straatcode" id="openbareruimte-straatcode" name="straatcode" data-cy="straatcode" type="text" />
              <ValidatedField label="Straatnaam" id="openbareruimte-straatnaam" name="straatnaam" data-cy="straatnaam" type="text" />
              <ValidatedField
                label="Typeopenbareruimte"
                id="openbareruimte-typeopenbareruimte"
                name="typeopenbareruimte"
                data-cy="typeopenbareruimte"
                type="text"
              />
              <ValidatedField label="Versie" id="openbareruimte-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField label="Wegsegment" id="openbareruimte-wegsegment" name="wegsegment" data-cy="wegsegment" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/openbareruimte" replace color="info">
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

export default OpenbareruimteUpdate;
