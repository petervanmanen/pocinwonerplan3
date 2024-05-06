import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon';
import VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDetail from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-detail';
import VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdate from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-update';
import VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDeleteDialog from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-delete-dialog';

const VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRoutes;
