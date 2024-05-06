import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEroute } from 'app/shared/model/eroute.model';
import { getEntity, updateEntity, createEntity, reset } from './eroute.reducer';

export const ErouteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const erouteEntity = useAppSelector(state => state.eroute.entity);
  const loading = useAppSelector(state => state.eroute.loading);
  const updating = useAppSelector(state => state.eroute.updating);
  const updateSuccess = useAppSelector(state => state.eroute.updateSuccess);

  const handleClose = () => {
    navigate('/eroute');
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
      ...erouteEntity,
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
          ...erouteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.eroute.home.createOrEditLabel" data-cy="ErouteCreateUpdateHeading">
            Create or edit a Eroute
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="eroute-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Geometrie" id="eroute-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Eroutecode" id="eroute-eroutecode" name="eroutecode" data-cy="eroutecode" type="text" />
              <ValidatedField label="Eroutesoort" id="eroute-eroutesoort" name="eroutesoort" data-cy="eroutesoort" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/eroute" replace color="info">
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

export default ErouteUpdate;
