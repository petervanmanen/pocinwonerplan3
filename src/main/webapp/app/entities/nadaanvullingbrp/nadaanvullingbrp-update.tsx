import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INadaanvullingbrp } from 'app/shared/model/nadaanvullingbrp.model';
import { getEntity, updateEntity, createEntity, reset } from './nadaanvullingbrp.reducer';

export const NadaanvullingbrpUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nadaanvullingbrpEntity = useAppSelector(state => state.nadaanvullingbrp.entity);
  const loading = useAppSelector(state => state.nadaanvullingbrp.loading);
  const updating = useAppSelector(state => state.nadaanvullingbrp.updating);
  const updateSuccess = useAppSelector(state => state.nadaanvullingbrp.updateSuccess);

  const handleClose = () => {
    navigate('/nadaanvullingbrp');
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
      ...nadaanvullingbrpEntity,
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
          ...nadaanvullingbrpEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.nadaanvullingbrp.home.createOrEditLabel" data-cy="NadaanvullingbrpCreateUpdateHeading">
            Create or edit a Nadaanvullingbrp
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
                <ValidatedField name="id" required readOnly id="nadaanvullingbrp-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Opmerkingen" id="nadaanvullingbrp-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nadaanvullingbrp" replace color="info">
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

export default NadaanvullingbrpUpdate;
