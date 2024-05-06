import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanwezigedeelnemer from './aanwezigedeelnemer';
import AanwezigedeelnemerDetail from './aanwezigedeelnemer-detail';
import AanwezigedeelnemerUpdate from './aanwezigedeelnemer-update';
import AanwezigedeelnemerDeleteDialog from './aanwezigedeelnemer-delete-dialog';

const AanwezigedeelnemerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanwezigedeelnemer />} />
    <Route path="new" element={<AanwezigedeelnemerUpdate />} />
    <Route path=":id">
      <Route index element={<AanwezigedeelnemerDetail />} />
      <Route path="edit" element={<AanwezigedeelnemerUpdate />} />
      <Route path="delete" element={<AanwezigedeelnemerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanwezigedeelnemerRoutes;
