import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Correspondentieadresbuitenland from './correspondentieadresbuitenland';
import CorrespondentieadresbuitenlandDetail from './correspondentieadresbuitenland-detail';
import CorrespondentieadresbuitenlandUpdate from './correspondentieadresbuitenland-update';
import CorrespondentieadresbuitenlandDeleteDialog from './correspondentieadresbuitenland-delete-dialog';

const CorrespondentieadresbuitenlandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Correspondentieadresbuitenland />} />
    <Route path="new" element={<CorrespondentieadresbuitenlandUpdate />} />
    <Route path=":id">
      <Route index element={<CorrespondentieadresbuitenlandDetail />} />
      <Route path="edit" element={<CorrespondentieadresbuitenlandUpdate />} />
      <Route path="delete" element={<CorrespondentieadresbuitenlandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CorrespondentieadresbuitenlandRoutes;
