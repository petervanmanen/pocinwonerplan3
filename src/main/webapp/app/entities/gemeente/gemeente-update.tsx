import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGemeente } from 'app/shared/model/gemeente.model';
import { getEntity, updateEntity, createEntity, reset } from './gemeente.reducer';

export const GemeenteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gemeenteEntity = useAppSelector(state => state.gemeente.entity);
  const loading = useAppSelector(state => state.gemeente.loading);
  const updating = useAppSelector(state => state.gemeente.updating);
  const updateSuccess = useAppSelector(state => state.gemeente.updateSuccess);

  const handleClose = () => {
    navigate('/gemeente');
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
      ...gemeenteEntity,
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
          ...gemeenteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gemeente.home.createOrEditLabel" data-cy="GemeenteCreateUpdateHeading">
            Create or edit a Gemeente
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="gemeente-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="gemeente-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="gemeente-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="gemeente-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="gemeente-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="gemeente-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Gemeentecode" id="gemeente-gemeentecode" name="gemeentecode" data-cy="gemeentecode" type="text" />
              <ValidatedField label="Gemeentenaam" id="gemeente-gemeentenaam" name="gemeentenaam" data-cy="gemeentenaam" type="text" />
              <ValidatedField
                label="Gemeentenaamnen"
                id="gemeente-gemeentenaamnen"
                name="gemeentenaamnen"
                data-cy="gemeentenaamnen"
                type="text"
              />
              <ValidatedField label="Geometrie" id="gemeente-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Identificatie" id="gemeente-identificatie" name="identificatie" data-cy="identificatie" type="text" />
              <ValidatedField
                label="Inonderzoek"
                id="gemeente-inonderzoek"
                name="inonderzoek"
                data-cy="inonderzoek"
                check
                type="checkbox"
              />
              <ValidatedField label="Versie" id="gemeente-versie" name="versie" data-cy="versie" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gemeente" replace color="info">
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

export default GemeenteUpdate;
