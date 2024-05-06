import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISpeelterrein } from 'app/shared/model/speelterrein.model';
import { getEntity, updateEntity, createEntity, reset } from './speelterrein.reducer';

export const SpeelterreinUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const speelterreinEntity = useAppSelector(state => state.speelterrein.entity);
  const loading = useAppSelector(state => state.speelterrein.loading);
  const updating = useAppSelector(state => state.speelterrein.updating);
  const updateSuccess = useAppSelector(state => state.speelterrein.updateSuccess);

  const handleClose = () => {
    navigate('/speelterrein');
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
      ...speelterreinEntity,
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
          ...speelterreinEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.speelterrein.home.createOrEditLabel" data-cy="SpeelterreinCreateUpdateHeading">
            Create or edit a Speelterrein
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="speelterrein-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Jaarherinrichting"
                id="speelterrein-jaarherinrichting"
                name="jaarherinrichting"
                data-cy="jaarherinrichting"
                type="text"
              />
              <ValidatedField
                label="Speelterreinleeftijddoelgroep"
                id="speelterrein-speelterreinleeftijddoelgroep"
                name="speelterreinleeftijddoelgroep"
                data-cy="speelterreinleeftijddoelgroep"
                type="text"
              />
              <ValidatedField label="Type" id="speelterrein-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="speelterrein-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/speelterrein" replace color="info">
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

export default SpeelterreinUpdate;
