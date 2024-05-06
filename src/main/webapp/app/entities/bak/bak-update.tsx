import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBak } from 'app/shared/model/bak.model';
import { getEntity, updateEntity, createEntity, reset } from './bak.reducer';

export const BakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bakEntity = useAppSelector(state => state.bak.entity);
  const loading = useAppSelector(state => state.bak.loading);
  const updating = useAppSelector(state => state.bak.updating);
  const updateSuccess = useAppSelector(state => state.bak.updateSuccess);

  const handleClose = () => {
    navigate('/bak');
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
      ...bakEntity,
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
          ...bakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bak.home.createOrEditLabel" data-cy="BakCreateUpdateHeading">
            Create or edit a Bak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="bak-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Diameter" id="bak-diameter" name="diameter" data-cy="diameter" type="text" />
              <ValidatedField label="Gewichtleeg" id="bak-gewichtleeg" name="gewichtleeg" data-cy="gewichtleeg" type="text" />
              <ValidatedField label="Gewichtvol" id="bak-gewichtvol" name="gewichtvol" data-cy="gewichtvol" type="text" />
              <ValidatedField label="Hoogte" id="bak-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField label="Inhoud" id="bak-inhoud" name="inhoud" data-cy="inhoud" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="bak-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="bak-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="bak-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="bak-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Materiaal" id="bak-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField
                label="Verplaatsbaar"
                id="bak-verplaatsbaar"
                name="verplaatsbaar"
                data-cy="verplaatsbaar"
                check
                type="checkbox"
              />
              <ValidatedField label="Vorm" id="bak-vorm" name="vorm" data-cy="vorm" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bak" replace color="info">
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

export default BakUpdate;
