import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INederlandsenationaliteitingeschrevenpersoon } from 'app/shared/model/nederlandsenationaliteitingeschrevenpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './nederlandsenationaliteitingeschrevenpersoon.reducer';

export const NederlandsenationaliteitingeschrevenpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nederlandsenationaliteitingeschrevenpersoonEntity = useAppSelector(
    state => state.nederlandsenationaliteitingeschrevenpersoon.entity,
  );
  const loading = useAppSelector(state => state.nederlandsenationaliteitingeschrevenpersoon.loading);
  const updating = useAppSelector(state => state.nederlandsenationaliteitingeschrevenpersoon.updating);
  const updateSuccess = useAppSelector(state => state.nederlandsenationaliteitingeschrevenpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/nederlandsenationaliteitingeschrevenpersoon');
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
      ...nederlandsenationaliteitingeschrevenpersoonEntity,
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
          ...nederlandsenationaliteitingeschrevenpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.nederlandsenationaliteitingeschrevenpersoon.home.createOrEditLabel"
            data-cy="NederlandsenationaliteitingeschrevenpersoonCreateUpdateHeading"
          >
            Create or edit a Nederlandsenationaliteitingeschrevenpersoon
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="nederlandsenationaliteitingeschrevenpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Aanduidingbijzondernederlanderschap"
                id="nederlandsenationaliteitingeschrevenpersoon-aanduidingbijzondernederlanderschap"
                name="aanduidingbijzondernederlanderschap"
                data-cy="aanduidingbijzondernederlanderschap"
                type="text"
              />
              <ValidatedField
                label="Nationaliteit"
                id="nederlandsenationaliteitingeschrevenpersoon-nationaliteit"
                name="nationaliteit"
                data-cy="nationaliteit"
                type="text"
              />
              <ValidatedField
                label="Redenverkrijgingnederlandsenationaliteit"
                id="nederlandsenationaliteitingeschrevenpersoon-redenverkrijgingnederlandsenationaliteit"
                name="redenverkrijgingnederlandsenationaliteit"
                data-cy="redenverkrijgingnederlandsenationaliteit"
                type="text"
              />
              <ValidatedField
                label="Redenverliesnederlandsenationaliteit"
                id="nederlandsenationaliteitingeschrevenpersoon-redenverliesnederlandsenationaliteit"
                name="redenverliesnederlandsenationaliteit"
                data-cy="redenverliesnederlandsenationaliteit"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/nederlandsenationaliteitingeschrevenpersoon"
                replace
                color="info"
              >
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

export default NederlandsenationaliteitingeschrevenpersoonUpdate;
