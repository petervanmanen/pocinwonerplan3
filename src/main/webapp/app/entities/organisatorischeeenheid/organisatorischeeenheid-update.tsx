import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrganisatorischeeenheid } from 'app/shared/model/organisatorischeeenheid.model';
import { getEntity, updateEntity, createEntity, reset } from './organisatorischeeenheid.reducer';

export const OrganisatorischeeenheidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const organisatorischeeenheidEntity = useAppSelector(state => state.organisatorischeeenheid.entity);
  const loading = useAppSelector(state => state.organisatorischeeenheid.loading);
  const updating = useAppSelector(state => state.organisatorischeeenheid.updating);
  const updateSuccess = useAppSelector(state => state.organisatorischeeenheid.updateSuccess);

  const handleClose = () => {
    navigate('/organisatorischeeenheid');
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
      ...organisatorischeeenheidEntity,
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
          ...organisatorischeeenheidEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.organisatorischeeenheid.home.createOrEditLabel" data-cy="OrganisatorischeeenheidCreateUpdateHeading">
            Create or edit a Organisatorischeeenheid
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
                <ValidatedField name="id" required readOnly id="organisatorischeeenheid-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumontstaan"
                id="organisatorischeeenheid-datumontstaan"
                name="datumontstaan"
                data-cy="datumontstaan"
                type="text"
              />
              <ValidatedField
                label="Datumopheffing"
                id="organisatorischeeenheid-datumopheffing"
                name="datumopheffing"
                data-cy="datumopheffing"
                type="text"
              />
              <ValidatedField
                label="Emailadres"
                id="organisatorischeeenheid-emailadres"
                name="emailadres"
                data-cy="emailadres"
                type="text"
              />
              <ValidatedField
                label="Faxnummer"
                id="organisatorischeeenheid-faxnummer"
                name="faxnummer"
                data-cy="faxnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Formatie" id="organisatorischeeenheid-formatie" name="formatie" data-cy="formatie" type="text" />
              <ValidatedField label="Naam" id="organisatorischeeenheid-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Naamverkort"
                id="organisatorischeeenheid-naamverkort"
                name="naamverkort"
                data-cy="naamverkort"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="organisatorischeeenheid-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Organisatieidentificatie"
                id="organisatorischeeenheid-organisatieidentificatie"
                name="organisatieidentificatie"
                data-cy="organisatieidentificatie"
                type="text"
              />
              <ValidatedField
                label="Telefoonnummer"
                id="organisatorischeeenheid-telefoonnummer"
                name="telefoonnummer"
                data-cy="telefoonnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Toelichting"
                id="organisatorischeeenheid-toelichting"
                name="toelichting"
                data-cy="toelichting"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/organisatorischeeenheid" replace color="info">
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

export default OrganisatorischeeenheidUpdate;
