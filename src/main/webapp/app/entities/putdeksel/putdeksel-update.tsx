import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPutdeksel } from 'app/shared/model/putdeksel.model';
import { getEntity, updateEntity, createEntity, reset } from './putdeksel.reducer';

export const PutdekselUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const putdekselEntity = useAppSelector(state => state.putdeksel.entity);
  const loading = useAppSelector(state => state.putdeksel.loading);
  const updating = useAppSelector(state => state.putdeksel.updating);
  const updateSuccess = useAppSelector(state => state.putdeksel.updateSuccess);

  const handleClose = () => {
    navigate('/putdeksel');
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
      ...putdekselEntity,
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
          ...putdekselEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.putdeksel.home.createOrEditLabel" data-cy="PutdekselCreateUpdateHeading">
            Create or edit a Putdeksel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="putdeksel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Diameter" id="putdeksel-diameter" name="diameter" data-cy="diameter" type="text" />
              <ValidatedField label="Put" id="putdeksel-put" name="put" data-cy="put" type="text" />
              <ValidatedField label="Type" id="putdeksel-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Vorm" id="putdeksel-vorm" name="vorm" data-cy="vorm" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/putdeksel" replace color="info">
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

export default PutdekselUpdate;
