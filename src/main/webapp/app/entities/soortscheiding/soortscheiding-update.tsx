import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISoortscheiding } from 'app/shared/model/soortscheiding.model';
import { getEntity, updateEntity, createEntity, reset } from './soortscheiding.reducer';

export const SoortscheidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const soortscheidingEntity = useAppSelector(state => state.soortscheiding.entity);
  const loading = useAppSelector(state => state.soortscheiding.loading);
  const updating = useAppSelector(state => state.soortscheiding.updating);
  const updateSuccess = useAppSelector(state => state.soortscheiding.updateSuccess);

  const handleClose = () => {
    navigate('/soortscheiding');
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
      ...soortscheidingEntity,
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
          ...soortscheidingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.soortscheiding.home.createOrEditLabel" data-cy="SoortscheidingCreateUpdateHeading">
            Create or edit a Soortscheiding
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
                <ValidatedField name="id" required readOnly id="soortscheiding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Indicatieplusbrpopulatie"
                id="soortscheiding-indicatieplusbrpopulatie"
                name="indicatieplusbrpopulatie"
                data-cy="indicatieplusbrpopulatie"
                type="text"
              />
              <ValidatedField
                label="Typescheiding"
                id="soortscheiding-typescheiding"
                name="typescheiding"
                data-cy="typescheiding"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/soortscheiding" replace color="info">
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

export default SoortscheidingUpdate;
