import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGemeentebegrafenis } from 'app/shared/model/gemeentebegrafenis.model';
import { getEntity, updateEntity, createEntity, reset } from './gemeentebegrafenis.reducer';

export const GemeentebegrafenisUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gemeentebegrafenisEntity = useAppSelector(state => state.gemeentebegrafenis.entity);
  const loading = useAppSelector(state => state.gemeentebegrafenis.loading);
  const updating = useAppSelector(state => state.gemeentebegrafenis.updating);
  const updateSuccess = useAppSelector(state => state.gemeentebegrafenis.updateSuccess);

  const handleClose = () => {
    navigate('/gemeentebegrafenis');
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
    if (values.begrafeniskosten !== undefined && typeof values.begrafeniskosten !== 'number') {
      values.begrafeniskosten = Number(values.begrafeniskosten);
    }
    if (values.gemeentelijkekosten !== undefined && typeof values.gemeentelijkekosten !== 'number') {
      values.gemeentelijkekosten = Number(values.gemeentelijkekosten);
    }
    if (values.verhaaldbedrag !== undefined && typeof values.verhaaldbedrag !== 'number') {
      values.verhaaldbedrag = Number(values.verhaaldbedrag);
    }

    const entity = {
      ...gemeentebegrafenisEntity,
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
          ...gemeentebegrafenisEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gemeentebegrafenis.home.createOrEditLabel" data-cy="GemeentebegrafenisCreateUpdateHeading">
            Create or edit a Gemeentebegrafenis
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
                <ValidatedField name="id" required readOnly id="gemeentebegrafenis-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Achtergrondmelding"
                id="gemeentebegrafenis-achtergrondmelding"
                name="achtergrondmelding"
                data-cy="achtergrondmelding"
                type="text"
              />
              <ValidatedField
                label="Begrafeniskosten"
                id="gemeentebegrafenis-begrafeniskosten"
                name="begrafeniskosten"
                data-cy="begrafeniskosten"
                type="text"
              />
              <ValidatedField
                label="Datumafgedaan"
                id="gemeentebegrafenis-datumafgedaan"
                name="datumafgedaan"
                data-cy="datumafgedaan"
                type="date"
              />
              <ValidatedField
                label="Datumbegrafenis"
                id="gemeentebegrafenis-datumbegrafenis"
                name="datumbegrafenis"
                data-cy="datumbegrafenis"
                type="date"
              />
              <ValidatedField
                label="Datumgemeld"
                id="gemeentebegrafenis-datumgemeld"
                name="datumgemeld"
                data-cy="datumgemeld"
                type="date"
              />
              <ValidatedField
                label="Datumruiminggraf"
                id="gemeentebegrafenis-datumruiminggraf"
                name="datumruiminggraf"
                data-cy="datumruiminggraf"
                type="date"
              />
              <ValidatedField
                label="Doodsoorzaak"
                id="gemeentebegrafenis-doodsoorzaak"
                name="doodsoorzaak"
                data-cy="doodsoorzaak"
                type="text"
              />
              <ValidatedField
                label="Gemeentelijkekosten"
                id="gemeentebegrafenis-gemeentelijkekosten"
                name="gemeentelijkekosten"
                data-cy="gemeentelijkekosten"
                type="text"
              />
              <ValidatedField
                label="Inkoopordernummer"
                id="gemeentebegrafenis-inkoopordernummer"
                name="inkoopordernummer"
                data-cy="inkoopordernummer"
                type="text"
              />
              <ValidatedField label="Melder" id="gemeentebegrafenis-melder" name="melder" data-cy="melder" type="text" />
              <ValidatedField
                label="Urengemeente"
                id="gemeentebegrafenis-urengemeente"
                name="urengemeente"
                data-cy="urengemeente"
                type="text"
              />
              <ValidatedField
                label="Verhaaldbedrag"
                id="gemeentebegrafenis-verhaaldbedrag"
                name="verhaaldbedrag"
                data-cy="verhaaldbedrag"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gemeentebegrafenis" replace color="info">
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

export default GemeentebegrafenisUpdate;
