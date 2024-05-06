import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerkeersdrempel } from 'app/shared/model/verkeersdrempel.model';
import { getEntity, updateEntity, createEntity, reset } from './verkeersdrempel.reducer';

export const VerkeersdrempelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verkeersdrempelEntity = useAppSelector(state => state.verkeersdrempel.entity);
  const loading = useAppSelector(state => state.verkeersdrempel.loading);
  const updating = useAppSelector(state => state.verkeersdrempel.updating);
  const updateSuccess = useAppSelector(state => state.verkeersdrempel.updateSuccess);

  const handleClose = () => {
    navigate('/verkeersdrempel');
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
      ...verkeersdrempelEntity,
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
          ...verkeersdrempelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verkeersdrempel.home.createOrEditLabel" data-cy="VerkeersdrempelCreateUpdateHeading">
            Create or edit a Verkeersdrempel
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
                <ValidatedField name="id" required readOnly id="verkeersdrempel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Ontwerpsnelheid"
                id="verkeersdrempel-ontwerpsnelheid"
                name="ontwerpsnelheid"
                data-cy="ontwerpsnelheid"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField label="Type" id="verkeersdrempel-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="verkeersdrempel-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verkeersdrempel" replace color="info">
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

export default VerkeersdrempelUpdate;
