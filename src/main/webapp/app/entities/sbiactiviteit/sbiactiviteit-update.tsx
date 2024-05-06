import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISbiactiviteit } from 'app/shared/model/sbiactiviteit.model';
import { getEntity, updateEntity, createEntity, reset } from './sbiactiviteit.reducer';

export const SbiactiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sbiactiviteitEntity = useAppSelector(state => state.sbiactiviteit.entity);
  const loading = useAppSelector(state => state.sbiactiviteit.loading);
  const updating = useAppSelector(state => state.sbiactiviteit.updating);
  const updateSuccess = useAppSelector(state => state.sbiactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/sbiactiviteit');
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
      ...sbiactiviteitEntity,
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
          ...sbiactiviteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sbiactiviteit.home.createOrEditLabel" data-cy="SbiactiviteitCreateUpdateHeading">
            Create or edit a Sbiactiviteit
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
                <ValidatedField name="id" required readOnly id="sbiactiviteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindesbiactiviteit"
                id="sbiactiviteit-datumeindesbiactiviteit"
                name="datumeindesbiactiviteit"
                data-cy="datumeindesbiactiviteit"
                type="date"
              />
              <ValidatedField
                label="Datumingangsbiactiviteit"
                id="sbiactiviteit-datumingangsbiactiviteit"
                name="datumingangsbiactiviteit"
                data-cy="datumingangsbiactiviteit"
                type="date"
              />
              <ValidatedField label="Hoofdniveau" id="sbiactiviteit-hoofdniveau" name="hoofdniveau" data-cy="hoofdniveau" type="text" />
              <ValidatedField
                label="Hoofdniveauomschrijving"
                id="sbiactiviteit-hoofdniveauomschrijving"
                name="hoofdniveauomschrijving"
                data-cy="hoofdniveauomschrijving"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Naamactiviteit"
                id="sbiactiviteit-naamactiviteit"
                name="naamactiviteit"
                data-cy="naamactiviteit"
                type="text"
              />
              <ValidatedField
                label="Sbicode"
                id="sbiactiviteit-sbicode"
                name="sbicode"
                data-cy="sbicode"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="Sbigroep"
                id="sbiactiviteit-sbigroep"
                name="sbigroep"
                data-cy="sbigroep"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="Sbigroepomschrijving"
                id="sbiactiviteit-sbigroepomschrijving"
                name="sbigroepomschrijving"
                data-cy="sbigroepomschrijving"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sbiactiviteit" replace color="info">
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

export default SbiactiviteitUpdate;
