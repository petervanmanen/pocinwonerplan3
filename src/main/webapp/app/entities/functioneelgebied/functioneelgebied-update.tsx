import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFunctioneelgebied } from 'app/shared/model/functioneelgebied.model';
import { getEntity, updateEntity, createEntity, reset } from './functioneelgebied.reducer';

export const FunctioneelgebiedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const functioneelgebiedEntity = useAppSelector(state => state.functioneelgebied.entity);
  const loading = useAppSelector(state => state.functioneelgebied.loading);
  const updating = useAppSelector(state => state.functioneelgebied.updating);
  const updateSuccess = useAppSelector(state => state.functioneelgebied.updateSuccess);

  const handleClose = () => {
    navigate('/functioneelgebied');
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
      ...functioneelgebiedEntity,
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
          ...functioneelgebiedEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.functioneelgebied.home.createOrEditLabel" data-cy="FunctioneelgebiedCreateUpdateHeading">
            Create or edit a Functioneelgebied
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
                <ValidatedField name="id" required readOnly id="functioneelgebied-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Functioneelgebiedcode"
                id="functioneelgebied-functioneelgebiedcode"
                name="functioneelgebiedcode"
                data-cy="functioneelgebiedcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Functioneelgebiednaam"
                id="functioneelgebied-functioneelgebiednaam"
                name="functioneelgebiednaam"
                data-cy="functioneelgebiednaam"
                type="text"
              />
              <ValidatedField label="Omtrek" id="functioneelgebied-omtrek" name="omtrek" data-cy="omtrek" type="text" />
              <ValidatedField label="Oppervlakte" id="functioneelgebied-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/functioneelgebied" replace color="info">
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

export default FunctioneelgebiedUpdate;
