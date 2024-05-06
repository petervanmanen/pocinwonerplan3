import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverlijdeningeschrevenpersoon } from 'app/shared/model/overlijdeningeschrevenpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './overlijdeningeschrevenpersoon.reducer';

export const OverlijdeningeschrevenpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overlijdeningeschrevenpersoonEntity = useAppSelector(state => state.overlijdeningeschrevenpersoon.entity);
  const loading = useAppSelector(state => state.overlijdeningeschrevenpersoon.loading);
  const updating = useAppSelector(state => state.overlijdeningeschrevenpersoon.updating);
  const updateSuccess = useAppSelector(state => state.overlijdeningeschrevenpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/overlijdeningeschrevenpersoon');
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
      ...overlijdeningeschrevenpersoonEntity,
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
          ...overlijdeningeschrevenpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overlijdeningeschrevenpersoon.home.createOrEditLabel" data-cy="OverlijdeningeschrevenpersoonCreateUpdateHeading">
            Create or edit a Overlijdeningeschrevenpersoon
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
                  id="overlijdeningeschrevenpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Datumoverlijden"
                id="overlijdeningeschrevenpersoon-datumoverlijden"
                name="datumoverlijden"
                data-cy="datumoverlijden"
                type="text"
              />
              <ValidatedField
                label="Landoverlijden"
                id="overlijdeningeschrevenpersoon-landoverlijden"
                name="landoverlijden"
                data-cy="landoverlijden"
                type="text"
              />
              <ValidatedField
                label="Overlijdensplaats"
                id="overlijdeningeschrevenpersoon-overlijdensplaats"
                name="overlijdensplaats"
                data-cy="overlijdensplaats"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/overlijdeningeschrevenpersoon"
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

export default OverlijdeningeschrevenpersoonUpdate;
