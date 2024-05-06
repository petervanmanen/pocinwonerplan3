import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeiding } from 'app/shared/model/leiding.model';
import { getEntity, updateEntity, createEntity, reset } from './leiding.reducer';

export const LeidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leidingEntity = useAppSelector(state => state.leiding.entity);
  const loading = useAppSelector(state => state.leiding.loading);
  const updating = useAppSelector(state => state.leiding.updating);
  const updateSuccess = useAppSelector(state => state.leiding.updateSuccess);

  const handleClose = () => {
    navigate('/leiding');
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
      ...leidingEntity,
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
          ...leidingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.leiding.home.createOrEditLabel" data-cy="LeidingCreateUpdateHeading">
            Create or edit a Leiding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="leiding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Afwijkendedieptelegging"
                id="leiding-afwijkendedieptelegging"
                name="afwijkendedieptelegging"
                data-cy="afwijkendedieptelegging"
                type="text"
              />
              <ValidatedField label="Breedte" id="leiding-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Diameter" id="leiding-diameter" name="diameter" data-cy="diameter" type="text" />
              <ValidatedField label="Diepte" id="leiding-diepte" name="diepte" data-cy="diepte" type="text" />
              <ValidatedField
                label="Eisvoorzorgsmaatregel"
                id="leiding-eisvoorzorgsmaatregel"
                name="eisvoorzorgsmaatregel"
                data-cy="eisvoorzorgsmaatregel"
                type="text"
              />
              <ValidatedField
                label="Geonauwkeurigheidxy"
                id="leiding-geonauwkeurigheidxy"
                name="geonauwkeurigheidxy"
                data-cy="geonauwkeurigheidxy"
                type="text"
              />
              <ValidatedField label="Hoogte" id="leiding-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="leiding-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Lengte" id="leiding-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="leiding-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Materiaal" id="leiding-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField label="Themaimkl" id="leiding-themaimkl" name="themaimkl" data-cy="themaimkl" type="text" />
              <ValidatedField
                label="Verhoogdrisico"
                id="leiding-verhoogdrisico"
                name="verhoogdrisico"
                data-cy="verhoogdrisico"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leiding" replace color="info">
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

export default LeidingUpdate;
