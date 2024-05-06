import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocatieonroerendezaak } from 'app/shared/model/locatieonroerendezaak.model';
import { getEntity, updateEntity, createEntity, reset } from './locatieonroerendezaak.reducer';

export const LocatieonroerendezaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locatieonroerendezaakEntity = useAppSelector(state => state.locatieonroerendezaak.entity);
  const loading = useAppSelector(state => state.locatieonroerendezaak.loading);
  const updating = useAppSelector(state => state.locatieonroerendezaak.updating);
  const updateSuccess = useAppSelector(state => state.locatieonroerendezaak.updateSuccess);

  const handleClose = () => {
    navigate('/locatieonroerendezaak');
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
      ...locatieonroerendezaakEntity,
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
          ...locatieonroerendezaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.locatieonroerendezaak.home.createOrEditLabel" data-cy="LocatieonroerendezaakCreateUpdateHeading">
            Create or edit a Locatieonroerendezaak
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
                <ValidatedField name="id" required readOnly id="locatieonroerendezaak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Adrestype" id="locatieonroerendezaak-adrestype" name="adrestype" data-cy="adrestype" type="text" />
              <ValidatedField
                label="Cultuurcodebebouwd"
                id="locatieonroerendezaak-cultuurcodebebouwd"
                name="cultuurcodebebouwd"
                data-cy="cultuurcodebebouwd"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheid"
                id="locatieonroerendezaak-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="locatieonroerendezaak-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Geometrie" id="locatieonroerendezaak-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField
                label="Locatieomschrijving"
                id="locatieonroerendezaak-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/locatieonroerendezaak" replace color="info">
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

export default LocatieonroerendezaakUpdate;
