import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerplichtingwmojeugd } from 'app/shared/model/verplichtingwmojeugd.model';
import { getEntity, updateEntity, createEntity, reset } from './verplichtingwmojeugd.reducer';

export const VerplichtingwmojeugdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verplichtingwmojeugdEntity = useAppSelector(state => state.verplichtingwmojeugd.entity);
  const loading = useAppSelector(state => state.verplichtingwmojeugd.loading);
  const updating = useAppSelector(state => state.verplichtingwmojeugd.updating);
  const updateSuccess = useAppSelector(state => state.verplichtingwmojeugd.updateSuccess);

  const handleClose = () => {
    navigate('/verplichtingwmojeugd');
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
      ...verplichtingwmojeugdEntity,
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
          ...verplichtingwmojeugdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verplichtingwmojeugd.home.createOrEditLabel" data-cy="VerplichtingwmojeugdCreateUpdateHeading">
            Create or edit a Verplichtingwmojeugd
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
                <ValidatedField name="id" required readOnly id="verplichtingwmojeugd-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Budgetsoort"
                id="verplichtingwmojeugd-budgetsoort"
                name="budgetsoort"
                data-cy="budgetsoort"
                type="text"
              />
              <ValidatedField
                label="Budgetsoortgroep"
                id="verplichtingwmojeugd-budgetsoortgroep"
                name="budgetsoortgroep"
                data-cy="budgetsoortgroep"
                type="text"
              />
              <ValidatedField
                label="Einddatumgepland"
                id="verplichtingwmojeugd-einddatumgepland"
                name="einddatumgepland"
                data-cy="einddatumgepland"
                type="text"
              />
              <ValidatedField
                label="Feitelijkeeinddatum"
                id="verplichtingwmojeugd-feitelijkeeinddatum"
                name="feitelijkeeinddatum"
                data-cy="feitelijkeeinddatum"
                type="text"
              />
              <ValidatedField label="Jaar" id="verplichtingwmojeugd-jaar" name="jaar" data-cy="jaar" type="text" />
              <ValidatedField
                label="Periodiciteit"
                id="verplichtingwmojeugd-periodiciteit"
                name="periodiciteit"
                data-cy="periodiciteit"
                type="text"
              />
              <ValidatedField
                label="Verplichtingsoort"
                id="verplichtingwmojeugd-verplichtingsoort"
                name="verplichtingsoort"
                data-cy="verplichtingsoort"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verplichtingwmojeugd" replace color="info">
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

export default VerplichtingwmojeugdUpdate;
