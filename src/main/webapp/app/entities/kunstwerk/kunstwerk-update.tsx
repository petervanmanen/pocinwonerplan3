import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKunstwerk } from 'app/shared/model/kunstwerk.model';
import { getEntity, updateEntity, createEntity, reset } from './kunstwerk.reducer';

export const KunstwerkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kunstwerkEntity = useAppSelector(state => state.kunstwerk.entity);
  const loading = useAppSelector(state => state.kunstwerk.loading);
  const updating = useAppSelector(state => state.kunstwerk.updating);
  const updateSuccess = useAppSelector(state => state.kunstwerk.updateSuccess);

  const handleClose = () => {
    navigate('/kunstwerk');
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
      ...kunstwerkEntity,
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
          ...kunstwerkEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kunstwerk.home.createOrEditLabel" data-cy="KunstwerkCreateUpdateHeading">
            Create or edit a Kunstwerk
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kunstwerk-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanleghoogte" id="kunstwerk-aanleghoogte" name="aanleghoogte" data-cy="aanleghoogte" type="text" />
              <ValidatedField
                label="Antigraffitivoorziening"
                id="kunstwerk-antigraffitivoorziening"
                name="antigraffitivoorziening"
                data-cy="antigraffitivoorziening"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Bereikbaarheid"
                id="kunstwerk-bereikbaarheid"
                name="bereikbaarheid"
                data-cy="bereikbaarheid"
                type="text"
              />
              <ValidatedField label="Breedte" id="kunstwerk-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField
                label="Constructietype"
                id="kunstwerk-constructietype"
                name="constructietype"
                data-cy="constructietype"
                type="text"
              />
              <ValidatedField label="Gewicht" id="kunstwerk-gewicht" name="gewicht" data-cy="gewicht" type="text" />
              <ValidatedField label="Hoogte" id="kunstwerk-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField label="Installateur" id="kunstwerk-installateur" name="installateur" data-cy="installateur" type="text" />
              <ValidatedField
                label="Jaarconserveren"
                id="kunstwerk-jaarconserveren"
                name="jaarconserveren"
                data-cy="jaarconserveren"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="kunstwerk-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Jaarrenovatie" id="kunstwerk-jaarrenovatie" name="jaarrenovatie" data-cy="jaarrenovatie" type="text" />
              <ValidatedField
                label="Jaarvervanging"
                id="kunstwerk-jaarvervanging"
                name="jaarvervanging"
                data-cy="jaarvervanging"
                type="text"
              />
              <ValidatedField
                label="Kilometreringbegin"
                id="kunstwerk-kilometreringbegin"
                name="kilometreringbegin"
                data-cy="kilometreringbegin"
                type="text"
              />
              <ValidatedField
                label="Kilometreringeinde"
                id="kunstwerk-kilometreringeinde"
                name="kilometreringeinde"
                data-cy="kilometreringeinde"
                type="text"
              />
              <ValidatedField label="Kleur" id="kunstwerk-kleur" name="kleur" data-cy="kleur" type="text" />
              <ValidatedField
                label="Kunstwerkbereikbaarheidplus"
                id="kunstwerk-kunstwerkbereikbaarheidplus"
                name="kunstwerkbereikbaarheidplus"
                data-cy="kunstwerkbereikbaarheidplus"
                type="text"
              />
              <ValidatedField
                label="Kunstwerkmateriaal"
                id="kunstwerk-kunstwerkmateriaal"
                name="kunstwerkmateriaal"
                data-cy="kunstwerkmateriaal"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="kunstwerk-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="kunstwerk-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="kunstwerk-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="kunstwerk-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Looprichel" id="kunstwerk-looprichel" name="looprichel" data-cy="looprichel" check type="checkbox" />
              <ValidatedField
                label="Minimumconditiescore"
                id="kunstwerk-minimumconditiescore"
                name="minimumconditiescore"
                data-cy="minimumconditiescore"
                type="text"
              />
              <ValidatedField label="Monument" id="kunstwerk-monument" name="monument" data-cy="monument" check type="checkbox" />
              <ValidatedField
                label="Monumentnummer"
                id="kunstwerk-monumentnummer"
                name="monumentnummer"
                data-cy="monumentnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Eobjectnaam" id="kunstwerk-eobjectnaam" name="eobjectnaam" data-cy="eobjectnaam" type="text" />
              <ValidatedField
                label="Eobjectnummer"
                id="kunstwerk-eobjectnummer"
                name="eobjectnummer"
                data-cy="eobjectnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Onderhoudsregime"
                id="kunstwerk-onderhoudsregime"
                name="onderhoudsregime"
                data-cy="onderhoudsregime"
                type="text"
              />
              <ValidatedField label="Oppervlakte" id="kunstwerk-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Orientatie" id="kunstwerk-orientatie" name="orientatie" data-cy="orientatie" type="text" />
              <ValidatedField
                label="Technischelevensduur"
                id="kunstwerk-technischelevensduur"
                name="technischelevensduur"
                data-cy="technischelevensduur"
                type="text"
              />
              <ValidatedField label="Typefundering" id="kunstwerk-typefundering" name="typefundering" data-cy="typefundering" type="text" />
              <ValidatedField label="Typemonument" id="kunstwerk-typemonument" name="typemonument" data-cy="typemonument" type="text" />
              <ValidatedField
                label="Vervangingswaarde"
                id="kunstwerk-vervangingswaarde"
                name="vervangingswaarde"
                data-cy="vervangingswaarde"
                type="text"
              />
              <ValidatedField
                label="Wegnummer"
                id="kunstwerk-wegnummer"
                name="wegnummer"
                data-cy="wegnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kunstwerk" replace color="info">
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

export default KunstwerkUpdate;
