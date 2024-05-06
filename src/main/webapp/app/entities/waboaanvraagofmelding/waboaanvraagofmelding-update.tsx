import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWaboaanvraagofmelding } from 'app/shared/model/waboaanvraagofmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './waboaanvraagofmelding.reducer';

export const WaboaanvraagofmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const waboaanvraagofmeldingEntity = useAppSelector(state => state.waboaanvraagofmelding.entity);
  const loading = useAppSelector(state => state.waboaanvraagofmelding.loading);
  const updating = useAppSelector(state => state.waboaanvraagofmelding.updating);
  const updateSuccess = useAppSelector(state => state.waboaanvraagofmelding.updateSuccess);

  const handleClose = () => {
    navigate('/waboaanvraagofmelding');
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
    if (values.bouwkosten !== undefined && typeof values.bouwkosten !== 'number') {
      values.bouwkosten = Number(values.bouwkosten);
    }
    if (values.projectkosten !== undefined && typeof values.projectkosten !== 'number') {
      values.projectkosten = Number(values.projectkosten);
    }

    const entity = {
      ...waboaanvraagofmeldingEntity,
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
          ...waboaanvraagofmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.waboaanvraagofmelding.home.createOrEditLabel" data-cy="WaboaanvraagofmeldingCreateUpdateHeading">
            Create or edit a Waboaanvraagofmelding
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
                <ValidatedField name="id" required readOnly id="waboaanvraagofmelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bouwkosten" id="waboaanvraagofmelding-bouwkosten" name="bouwkosten" data-cy="bouwkosten" type="text" />
              <ValidatedField label="Olonummer" id="waboaanvraagofmelding-olonummer" name="olonummer" data-cy="olonummer" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="waboaanvraagofmelding-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Projectkosten"
                id="waboaanvraagofmelding-projectkosten"
                name="projectkosten"
                data-cy="projectkosten"
                type="text"
              />
              <ValidatedField
                label="Registratienummer"
                id="waboaanvraagofmelding-registratienummer"
                name="registratienummer"
                data-cy="registratienummer"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/waboaanvraagofmelding" replace color="info">
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

export default WaboaanvraagofmeldingUpdate;
