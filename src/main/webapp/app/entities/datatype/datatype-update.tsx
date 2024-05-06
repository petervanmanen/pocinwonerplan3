import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDatatype } from 'app/shared/model/datatype.model';
import { getEntity, updateEntity, createEntity, reset } from './datatype.reducer';

export const DatatypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const datatypeEntity = useAppSelector(state => state.datatype.entity);
  const loading = useAppSelector(state => state.datatype.loading);
  const updating = useAppSelector(state => state.datatype.updating);
  const updateSuccess = useAppSelector(state => state.datatype.updateSuccess);

  const handleClose = () => {
    navigate('/datatype');
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
    const entity = {
      ...datatypeEntity,
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
          ...datatypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.datatype.home.createOrEditLabel" data-cy="DatatypeCreateUpdateHeading">
            Create or edit a Datatype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="datatype-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumopname" id="datatype-datumopname" name="datumopname" data-cy="datumopname" type="date" />
              <ValidatedField label="Definitie" id="datatype-definitie" name="definitie" data-cy="definitie" type="text" />
              <ValidatedField label="Domein" id="datatype-domein" name="domein" data-cy="domein" type="text" />
              <ValidatedField label="Eaguid" id="datatype-eaguid" name="eaguid" data-cy="eaguid" type="text" />
              <ValidatedField label="Herkomst" id="datatype-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField label="Kardinaliteit" id="datatype-kardinaliteit" name="kardinaliteit" data-cy="kardinaliteit" type="text" />
              <ValidatedField label="Lengte" id="datatype-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Naam" id="datatype-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Patroon" id="datatype-patroon" name="patroon" data-cy="patroon" type="text" />
              <ValidatedField label="Toelichting" id="datatype-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/datatype" replace color="info">
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

export default DatatypeUpdate;
