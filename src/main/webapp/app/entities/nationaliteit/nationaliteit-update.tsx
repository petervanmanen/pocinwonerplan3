import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INationaliteit } from 'app/shared/model/nationaliteit.model';
import { getEntity, updateEntity, createEntity, reset } from './nationaliteit.reducer';

export const NationaliteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nationaliteitEntity = useAppSelector(state => state.nationaliteit.entity);
  const loading = useAppSelector(state => state.nationaliteit.loading);
  const updating = useAppSelector(state => state.nationaliteit.updating);
  const updateSuccess = useAppSelector(state => state.nationaliteit.updateSuccess);

  const handleClose = () => {
    navigate('/nationaliteit');
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
      ...nationaliteitEntity,
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
          ...nationaliteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.nationaliteit.home.createOrEditLabel" data-cy="NationaliteitCreateUpdateHeading">
            Create or edit a Nationaliteit
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
                <ValidatedField name="id" required readOnly id="nationaliteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Buitenlandsenationaliteit"
                id="nationaliteit-buitenlandsenationaliteit"
                name="buitenlandsenationaliteit"
                data-cy="buitenlandsenationaliteit"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="nationaliteit-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datuminganggeldigheid"
                id="nationaliteit-datuminganggeldigheid"
                name="datuminganggeldigheid"
                data-cy="datuminganggeldigheid"
                type="date"
              />
              <ValidatedField label="Datumopnamen" id="nationaliteit-datumopnamen" name="datumopnamen" data-cy="datumopnamen" type="text" />
              <ValidatedField
                label="Datumverliesnationaliteit"
                id="nationaliteit-datumverliesnationaliteit"
                name="datumverliesnationaliteit"
                data-cy="datumverliesnationaliteit"
                type="date"
              />
              <ValidatedField
                label="Nationaliteit"
                id="nationaliteit-nationaliteit"
                name="nationaliteit"
                data-cy="nationaliteit"
                type="text"
              />
              <ValidatedField
                label="Nationaliteitcode"
                id="nationaliteit-nationaliteitcode"
                name="nationaliteitcode"
                data-cy="nationaliteitcode"
                type="text"
              />
              <ValidatedField
                label="Redenverkrijgingnederlandsenationaliteit"
                id="nationaliteit-redenverkrijgingnederlandsenationaliteit"
                name="redenverkrijgingnederlandsenationaliteit"
                data-cy="redenverkrijgingnederlandsenationaliteit"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Redenverliesnederlandsenationaliteit"
                id="nationaliteit-redenverliesnederlandsenationaliteit"
                name="redenverliesnederlandsenationaliteit"
                data-cy="redenverliesnederlandsenationaliteit"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nationaliteit" replace color="info">
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

export default NationaliteitUpdate;
