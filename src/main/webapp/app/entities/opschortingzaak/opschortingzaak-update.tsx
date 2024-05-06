import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOpschortingzaak } from 'app/shared/model/opschortingzaak.model';
import { getEntity, updateEntity, createEntity, reset } from './opschortingzaak.reducer';

export const OpschortingzaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const opschortingzaakEntity = useAppSelector(state => state.opschortingzaak.entity);
  const loading = useAppSelector(state => state.opschortingzaak.loading);
  const updating = useAppSelector(state => state.opschortingzaak.updating);
  const updateSuccess = useAppSelector(state => state.opschortingzaak.updateSuccess);

  const handleClose = () => {
    navigate('/opschortingzaak');
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
      ...opschortingzaakEntity,
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
          ...opschortingzaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.opschortingzaak.home.createOrEditLabel" data-cy="OpschortingzaakCreateUpdateHeading">
            Create or edit a Opschortingzaak
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
                <ValidatedField name="id" required readOnly id="opschortingzaak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Indicatieopschorting"
                id="opschortingzaak-indicatieopschorting"
                name="indicatieopschorting"
                data-cy="indicatieopschorting"
                type="text"
              />
              <ValidatedField
                label="Redenopschorting"
                id="opschortingzaak-redenopschorting"
                name="redenopschorting"
                data-cy="redenopschorting"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opschortingzaak" replace color="info">
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

export default OpschortingzaakUpdate;
