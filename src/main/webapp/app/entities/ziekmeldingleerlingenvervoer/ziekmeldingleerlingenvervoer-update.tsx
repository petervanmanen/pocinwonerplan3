import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZiekmeldingleerlingenvervoer } from 'app/shared/model/ziekmeldingleerlingenvervoer.model';
import { getEntity, updateEntity, createEntity, reset } from './ziekmeldingleerlingenvervoer.reducer';

export const ZiekmeldingleerlingenvervoerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ziekmeldingleerlingenvervoerEntity = useAppSelector(state => state.ziekmeldingleerlingenvervoer.entity);
  const loading = useAppSelector(state => state.ziekmeldingleerlingenvervoer.loading);
  const updating = useAppSelector(state => state.ziekmeldingleerlingenvervoer.updating);
  const updateSuccess = useAppSelector(state => state.ziekmeldingleerlingenvervoer.updateSuccess);

  const handleClose = () => {
    navigate('/ziekmeldingleerlingenvervoer');
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
      ...ziekmeldingleerlingenvervoerEntity,
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
          ...ziekmeldingleerlingenvervoerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ziekmeldingleerlingenvervoer.home.createOrEditLabel" data-cy="ZiekmeldingleerlingenvervoerCreateUpdateHeading">
            Create or edit a Ziekmeldingleerlingenvervoer
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
                <ValidatedField name="id" required readOnly id="ziekmeldingleerlingenvervoer-id" label="ID" validate={{ required: true }} />
              ) : null}
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/ziekmeldingleerlingenvervoer"
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

export default ZiekmeldingleerlingenvervoerUpdate;