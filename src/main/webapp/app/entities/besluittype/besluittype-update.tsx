import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBesluittype } from 'app/shared/model/besluittype.model';
import { getEntity, updateEntity, createEntity, reset } from './besluittype.reducer';

export const BesluittypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const besluittypeEntity = useAppSelector(state => state.besluittype.entity);
  const loading = useAppSelector(state => state.besluittype.loading);
  const updating = useAppSelector(state => state.besluittype.updating);
  const updateSuccess = useAppSelector(state => state.besluittype.updateSuccess);

  const handleClose = () => {
    navigate('/besluittype');
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
      ...besluittypeEntity,
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
          ...besluittypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.besluittype.home.createOrEditLabel" data-cy="BesluittypeCreateUpdateHeading">
            Create or edit a Besluittype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="besluittype-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Besluitcategorie"
                id="besluittype-besluitcategorie"
                name="besluitcategorie"
                data-cy="besluitcategorie"
                type="text"
              />
              <ValidatedField
                label="Besluittypeomschrijving"
                id="besluittype-besluittypeomschrijving"
                name="besluittypeomschrijving"
                data-cy="besluittypeomschrijving"
                type="text"
              />
              <ValidatedField
                label="Besluittypeomschrijvinggeneriek"
                id="besluittype-besluittypeomschrijvinggeneriek"
                name="besluittypeomschrijvinggeneriek"
                data-cy="besluittypeomschrijvinggeneriek"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidbesluittype"
                id="besluittype-datumbegingeldigheidbesluittype"
                name="datumbegingeldigheidbesluittype"
                data-cy="datumbegingeldigheidbesluittype"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheidbesluittype"
                id="besluittype-datumeindegeldigheidbesluittype"
                name="datumeindegeldigheidbesluittype"
                data-cy="datumeindegeldigheidbesluittype"
                type="text"
              />
              <ValidatedField
                label="Indicatiepublicatie"
                id="besluittype-indicatiepublicatie"
                name="indicatiepublicatie"
                data-cy="indicatiepublicatie"
                type="text"
              />
              <ValidatedField
                label="Publicatietekst"
                id="besluittype-publicatietekst"
                name="publicatietekst"
                data-cy="publicatietekst"
                type="text"
              />
              <ValidatedField
                label="Publicatietermijn"
                id="besluittype-publicatietermijn"
                name="publicatietermijn"
                data-cy="publicatietermijn"
                type="text"
              />
              <ValidatedField
                label="Reactietermijn"
                id="besluittype-reactietermijn"
                name="reactietermijn"
                data-cy="reactietermijn"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/besluittype" replace color="info">
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

export default BesluittypeUpdate;
