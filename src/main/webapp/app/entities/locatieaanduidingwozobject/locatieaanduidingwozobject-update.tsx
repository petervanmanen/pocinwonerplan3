import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocatieaanduidingwozobject } from 'app/shared/model/locatieaanduidingwozobject.model';
import { getEntity, updateEntity, createEntity, reset } from './locatieaanduidingwozobject.reducer';

export const LocatieaanduidingwozobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locatieaanduidingwozobjectEntity = useAppSelector(state => state.locatieaanduidingwozobject.entity);
  const loading = useAppSelector(state => state.locatieaanduidingwozobject.loading);
  const updating = useAppSelector(state => state.locatieaanduidingwozobject.updating);
  const updateSuccess = useAppSelector(state => state.locatieaanduidingwozobject.updateSuccess);

  const handleClose = () => {
    navigate('/locatieaanduidingwozobject');
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
      ...locatieaanduidingwozobjectEntity,
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
          ...locatieaanduidingwozobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.locatieaanduidingwozobject.home.createOrEditLabel" data-cy="LocatieaanduidingwozobjectCreateUpdateHeading">
            Create or edit a Locatieaanduidingwozobject
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
                <ValidatedField name="id" required readOnly id="locatieaanduidingwozobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="locatieaanduidingwozobject-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="locatieaanduidingwozobject-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField
                label="Locatieomschrijving"
                id="locatieaanduidingwozobject-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Primair" id="locatieaanduidingwozobject-primair" name="primair" data-cy="primair" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/locatieaanduidingwozobject" replace color="info">
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

export default LocatieaanduidingwozobjectUpdate;
