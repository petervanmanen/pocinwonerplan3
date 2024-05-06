import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICorrespondentieadresbuitenland } from 'app/shared/model/correspondentieadresbuitenland.model';
import { getEntity, updateEntity, createEntity, reset } from './correspondentieadresbuitenland.reducer';

export const CorrespondentieadresbuitenlandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const correspondentieadresbuitenlandEntity = useAppSelector(state => state.correspondentieadresbuitenland.entity);
  const loading = useAppSelector(state => state.correspondentieadresbuitenland.loading);
  const updating = useAppSelector(state => state.correspondentieadresbuitenland.updating);
  const updateSuccess = useAppSelector(state => state.correspondentieadresbuitenland.updateSuccess);

  const handleClose = () => {
    navigate('/correspondentieadresbuitenland');
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
      ...correspondentieadresbuitenlandEntity,
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
          ...correspondentieadresbuitenlandEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.correspondentieadresbuitenland.home.createOrEditLabel"
            data-cy="CorrespondentieadresbuitenlandCreateUpdateHeading"
          >
            Create or edit a Correspondentieadresbuitenland
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
                  id="correspondentieadresbuitenland-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Adresbuitenland 1"
                id="correspondentieadresbuitenland-adresbuitenland1"
                name="adresbuitenland1"
                data-cy="adresbuitenland1"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 2"
                id="correspondentieadresbuitenland-adresbuitenland2"
                name="adresbuitenland2"
                data-cy="adresbuitenland2"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 3"
                id="correspondentieadresbuitenland-adresbuitenland3"
                name="adresbuitenland3"
                data-cy="adresbuitenland3"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 4"
                id="correspondentieadresbuitenland-adresbuitenland4"
                name="adresbuitenland4"
                data-cy="adresbuitenland4"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 5"
                id="correspondentieadresbuitenland-adresbuitenland5"
                name="adresbuitenland5"
                data-cy="adresbuitenland5"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 6"
                id="correspondentieadresbuitenland-adresbuitenland6"
                name="adresbuitenland6"
                data-cy="adresbuitenland6"
                type="text"
              />
              <ValidatedField
                label="Landcorrespondentieadres"
                id="correspondentieadresbuitenland-landcorrespondentieadres"
                name="landcorrespondentieadres"
                data-cy="landcorrespondentieadres"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/correspondentieadresbuitenland"
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

export default CorrespondentieadresbuitenlandUpdate;
