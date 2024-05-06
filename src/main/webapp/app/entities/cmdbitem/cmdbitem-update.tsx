import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICmdbitem } from 'app/shared/model/cmdbitem.model';
import { getEntity, updateEntity, createEntity, reset } from './cmdbitem.reducer';

export const CmdbitemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cmdbitemEntity = useAppSelector(state => state.cmdbitem.entity);
  const loading = useAppSelector(state => state.cmdbitem.loading);
  const updating = useAppSelector(state => state.cmdbitem.updating);
  const updateSuccess = useAppSelector(state => state.cmdbitem.updateSuccess);

  const handleClose = () => {
    navigate('/cmdbitem');
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
      ...cmdbitemEntity,
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
          ...cmdbitemEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.cmdbitem.home.createOrEditLabel" data-cy="CmdbitemCreateUpdateHeading">
            Create or edit a Cmdbitem
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="cmdbitem-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Beschrijving" id="cmdbitem-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField label="Naam" id="cmdbitem-naam" name="naam" data-cy="naam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cmdbitem" replace color="info">
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

export default CmdbitemUpdate;
