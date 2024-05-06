import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHuurwoningen } from 'app/shared/model/huurwoningen.model';
import { getEntity, updateEntity, createEntity, reset } from './huurwoningen.reducer';

export const HuurwoningenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const huurwoningenEntity = useAppSelector(state => state.huurwoningen.entity);
  const loading = useAppSelector(state => state.huurwoningen.loading);
  const updating = useAppSelector(state => state.huurwoningen.updating);
  const updateSuccess = useAppSelector(state => state.huurwoningen.updateSuccess);

  const handleClose = () => {
    navigate('/huurwoningen');
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
    if (values.huurprijs !== undefined && typeof values.huurprijs !== 'number') {
      values.huurprijs = Number(values.huurprijs);
    }

    const entity = {
      ...huurwoningenEntity,
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
          ...huurwoningenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.huurwoningen.home.createOrEditLabel" data-cy="HuurwoningenCreateUpdateHeading">
            Create or edit a Huurwoningen
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="huurwoningen-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Huurprijs" id="huurwoningen-huurprijs" name="huurprijs" data-cy="huurprijs" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/huurwoningen" replace color="info">
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

export default HuurwoningenUpdate;
