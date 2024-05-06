import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInfiltratieput } from 'app/shared/model/infiltratieput.model';
import { getEntity, updateEntity, createEntity, reset } from './infiltratieput.reducer';

export const InfiltratieputUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const infiltratieputEntity = useAppSelector(state => state.infiltratieput.entity);
  const loading = useAppSelector(state => state.infiltratieput.loading);
  const updating = useAppSelector(state => state.infiltratieput.updating);
  const updateSuccess = useAppSelector(state => state.infiltratieput.updateSuccess);

  const handleClose = () => {
    navigate('/infiltratieput');
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
      ...infiltratieputEntity,
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
          ...infiltratieputEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.infiltratieput.home.createOrEditLabel" data-cy="InfiltratieputCreateUpdateHeading">
            Create or edit a Infiltratieput
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
                <ValidatedField name="id" required readOnly id="infiltratieput-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Porositeit" id="infiltratieput-porositeit" name="porositeit" data-cy="porositeit" type="text" />
              <ValidatedField
                label="Risicogebied"
                id="infiltratieput-risicogebied"
                name="risicogebied"
                data-cy="risicogebied"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/infiltratieput" replace color="info">
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

export default InfiltratieputUpdate;
