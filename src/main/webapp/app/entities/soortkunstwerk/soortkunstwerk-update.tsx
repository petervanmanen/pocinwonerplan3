import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISoortkunstwerk } from 'app/shared/model/soortkunstwerk.model';
import { getEntity, updateEntity, createEntity, reset } from './soortkunstwerk.reducer';

export const SoortkunstwerkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const soortkunstwerkEntity = useAppSelector(state => state.soortkunstwerk.entity);
  const loading = useAppSelector(state => state.soortkunstwerk.loading);
  const updating = useAppSelector(state => state.soortkunstwerk.updating);
  const updateSuccess = useAppSelector(state => state.soortkunstwerk.updateSuccess);

  const handleClose = () => {
    navigate('/soortkunstwerk');
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
      ...soortkunstwerkEntity,
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
          ...soortkunstwerkEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.soortkunstwerk.home.createOrEditLabel" data-cy="SoortkunstwerkCreateUpdateHeading">
            Create or edit a Soortkunstwerk
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
                <ValidatedField name="id" required readOnly id="soortkunstwerk-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Indicatieplusbrpopulatie"
                id="soortkunstwerk-indicatieplusbrpopulatie"
                name="indicatieplusbrpopulatie"
                data-cy="indicatieplusbrpopulatie"
                type="text"
              />
              <ValidatedField
                label="Typekunstwerk"
                id="soortkunstwerk-typekunstwerk"
                name="typekunstwerk"
                data-cy="typekunstwerk"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/soortkunstwerk" replace color="info">
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

export default SoortkunstwerkUpdate;
